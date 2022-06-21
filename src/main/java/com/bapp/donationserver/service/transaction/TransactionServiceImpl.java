package com.bapp.donationserver.service.transaction;

import com.bapp.donationserver.data.*;
import com.bapp.donationserver.data.consts.BlockChainConst;
import com.bapp.donationserver.data.dto.*;
import com.bapp.donationserver.data.type.TransactionType;
import com.bapp.donationserver.exception.IllegalUserDataException;
import com.bapp.donationserver.repository.TransactionRepository;
import com.bapp.donationserver.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public void pay(Member member, Long amount) {

        Wallet wallet = member.getWallet();

        Long afterAmount = wallet.getAmount() + amount;

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setFrom(BlockChainConst.OWNER_ADDRESS);
        transaction.setTo(wallet.getId());
        transaction.setFromBalance(-1L);
        transaction.setToBalance(afterAmount);
        transaction.setType(TransactionType.PAY);
        transaction.setDate(LocalDateTime.now());
        transaction.setDetail(null);

        transactionRepository.save(BlockChainConst.OWNER_PRIVATE_KEY, transaction, null);

        wallet.setAmount(afterAmount);
        walletRepository.update(wallet);
    }

    @Override//추후 결제 대행사를 통한 결제 시스템 구축시에 변경사항이 생길 수도 있음
    public void payback(Member member, Long amount) {

        Wallet wallet = member.getWallet();

        Long afterAmount = wallet.getAmount() - amount;

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTo(BlockChainConst.OWNER_ADDRESS);
        transaction.setFrom(wallet.getId());
        transaction.setToBalance(-1L);
        transaction.setFromBalance(afterAmount);
        transaction.setType(TransactionType.PAYBACK);
        transaction.setDate(LocalDateTime.now());
        transaction.setDetail(null);

        transactionRepository.save(wallet.getPrivateKey(), transaction, null);

        wallet.setAmount(afterAmount);
        walletRepository.update(wallet);
    }

    @Transactional(readOnly = true)
    @Override
    public List<TransactionDetailDto> getTransactionHistory(Long campaignId) {
        List<TransactionDetailDto> dtoList = new ArrayList<>();
        transactionRepository.findByCampaignId(campaignId).forEach(transaction -> dtoList.add(transaction.getDetailDto()));
        return dtoList;
    }

    @Override
    public List<TransactionDto> getTransactionHistory(Wallet wallet) {
        List<TransactionDto> dtoList = new ArrayList<>();

        transactionRepository.findByWalletId(wallet.getId()).forEach(transaction -> dtoList.add(transaction.getDto()));
        return dtoList;
    }

    @Override
    public void withdraw(Campaign campaign, Member member, TransactionDetailDto dto) {

        //인출 가능한 금액 인지 확인
        Wallet from = campaign.getWallet();
        Wallet to = member.getWallet();

        log.info("temper : to={}, from={}", to, from);
        if(!campaign.getCharityName().equals(member.getName())){
            throw new IllegalArgumentException("해당 켐페인 출금이 불가능한 유저입니다.");
        }
        if (from.getAmount() < dto.getAmount()) {
            throw new IllegalArgumentException("인출 금액이 너무 큽니다.");
        }
        //인출 계산
        Long fromAfterAmount = from.getAmount() - dto.getAmount();
        Long toAfterAmount = to.getAmount() + dto.getAmount();

        //인출 : 거래 내역 등록, 켐패인 갱신
        TransactionDetail detail = new TransactionDetail();

        detail.setTransaction(new Transaction());
        detail.setSender(dto.getSender());
        detail.setReceiver(dto.getReceiver());
        detail.setPurpose(dto.getPurpose());

        Transaction transaction = detail.getTransaction();
        transaction.setAmount(dto.getAmount());
        transaction.setTo(to.getId());
        transaction.setFrom(from.getId());
        transaction.setToBalance(toAfterAmount);
        transaction.setFromBalance(fromAfterAmount);
        transaction.setType(TransactionType.WITHDRAW);
        transaction.setDate(LocalDateTime.now());
        transaction.setDetail(detail);

        //block chain 저장
        transactionRepository.save(from.getPrivateKey(), transaction, detail);

        //변경 사항 적용 후 db 저장
        from.setAmount(fromAfterAmount);
        to.setAmount(toAfterAmount);
        walletRepository.update(to);
        walletRepository.update(from);
    }

    @Override
    public void donate(Member member, Campaign campaign, Long amount) {

        Wallet from = member.getWallet();
        Wallet to = campaign.getWallet();

        //겁증
        if(from.getAmount() < amount)
            throw new IllegalUserDataException("포인트가 적습니다. 충전해주세요");

        //인출 계산
        Long fromAfterAmount = from.getAmount() - amount;
        Long toAfterAmount = to.getAmount() + amount;

        //거래 내역 생성
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTo(to.getId());
        transaction.setFrom(from.getId());
        transaction.setToBalance(toAfterAmount);
        transaction.setFromBalance(fromAfterAmount);
        transaction.setType(TransactionType.DONATION);
        transaction.setDate(LocalDateTime.now());

        //block chain 저장
        transactionRepository.save(from.getPrivateKey(), transaction, null);

        //변경 사항 적용 후 db 저장
        from.setAmount(fromAfterAmount);
        to.setAmount(toAfterAmount);
        walletRepository.update(from);
        walletRepository.update(to);
    }
}
