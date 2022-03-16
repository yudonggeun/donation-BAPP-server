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
@Transactional(readOnly = true)
public class TransactionServiceImpl implements TransactionService {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public void pay(Member member, Long amount) {

        Wallet wallet = member.getWallet();
        wallet.setAmount(wallet.getAmount() + amount);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setFrom("PROVIDER");
        transaction.setTo(member.getWallet().getId());
        transaction.setFromBalance(-1L);
        transaction.setToBalance(member.getWallet().getAmount());
        transaction.setType(TransactionType.PAY);
        transaction.setDate(LocalDate.now());
        transaction.setDetail(null);

        walletRepository.update(wallet);
        transactionRepository.save(transaction, null);
    }

    @Override
    public List<TransactionDto> getDonationHistory(Long campaignId) {
        List<TransactionDto> dtoList = new ArrayList<>();
        transactionRepository.findByCampaignId(campaignId).forEach(transaction -> dtoList.add(transaction.getDto()));
        return dtoList;
    }

    @Transactional
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

        transactionRepository.save(transaction, detail);
    }
}
