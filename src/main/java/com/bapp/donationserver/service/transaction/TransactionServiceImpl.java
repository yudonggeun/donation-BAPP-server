package com.bapp.donationserver.service.transaction;

import com.bapp.donationserver.data.*;
import com.bapp.donationserver.data.dto.*;
import com.bapp.donationserver.data.type.TransactionType;
import com.bapp.donationserver.repository.TransactionRepository;
import com.bapp.donationserver.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
        wallet.setAmount(wallet.getAmount() + amount);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setFrom("PROVIDER");
        transaction.setTo(wallet.getId());
        transaction.setFromBalance(-1L);
        transaction.setToBalance(wallet.getAmount());
        transaction.setType(TransactionType.PAY);
        transaction.setDate(LocalDate.now());
        transaction.setDetail(null);

        walletRepository.update(wallet);
        transactionRepository.save(transaction, null);
    }

    @Override//추후 결제 대행사를 통한 결제 시스템 구축시에 변경사항이 생길 수도 있음
    public void payback(Member member, Long amount) {

        Wallet wallet = member.getWallet();
        wallet.setAmount(wallet.getAmount() - amount);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTo("PROVIDER");
        transaction.setFrom(wallet.getId());
        transaction.setToBalance(-1L);
        transaction.setFromBalance(wallet.getAmount());
        transaction.setType(TransactionType.PAYBACK);
        transaction.setDate(LocalDate.now());
        transaction.setDetail(null);

        walletRepository.update(wallet);
        transactionRepository.save(transaction, null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<TransactionDto> getDonationHistory(Long campaignId) {
        List<TransactionDto> dtoList = new ArrayList<>();
        transactionRepository.findByCampaignId(campaignId).forEach(transaction -> dtoList.add(transaction.getDto()));
        return dtoList;
    }

    @Override
    public void withdraw(Campaign campaign, Member member, TransactionDto dto) {

        //인출 가능한 금액인지 확인
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
        from.setAmount(from.getAmount() - dto.getAmount());
        to.setAmount(to.getAmount() + dto.getAmount());

        //인출 : 거래 내역 등록, 켐패인 갱신
        TransactionDetail detail = new TransactionDetail();
        Transaction transaction = new Transaction(
                from,
                to,
                dto.getAmount(),
                TransactionType.WITHDRAW,
                detail
        );
        detail.setTransaction(transaction);
        detail.setSender(dto.getSender());
        detail.setReceiver(dto.getReceiver());
        detail.setPurpose(dto.getPurpose());

        //db 저장
        walletRepository.update(from);
        walletRepository.update(to);

        transactionRepository.save(transaction, detail);
    }

    @Override
    public void donate(Member member, Campaign campaign, Long amount) {

        Wallet memberWallet = member.getWallet();
        Wallet campaignWallet = campaign.getWallet();

        //겁증
        if(memberWallet.getAmount() < amount)
            throw new IllegalArgumentException("포인트가 적습니다. 충전해주세요");

        //인출 계산
        memberWallet.setAmount(memberWallet.getAmount() - amount);
        campaignWallet.setAmount(campaignWallet.getAmount() + amount);

        //거래 내역 생성
        Transaction transaction = new Transaction(
                memberWallet,
                campaignWallet,
                amount,
                TransactionType.DONATION,
                null
        );

        //db 저장
        walletRepository.update(memberWallet);
        walletRepository.update(campaignWallet);

        transactionRepository.save(transaction, null);
    }
}
