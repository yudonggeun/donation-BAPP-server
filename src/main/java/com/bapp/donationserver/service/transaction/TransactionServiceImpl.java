package com.bapp.donationserver.service.transaction;

import com.bapp.donationserver.data.*;
import com.bapp.donationserver.data.dto.*;
import com.bapp.donationserver.repository.TransactionRepository;
import com.bapp.donationserver.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void pay(String walletId, Long amount) {
        log.info("결제 완료");

        Wallet wallet = walletRepository.getWallet(walletId);
        wallet.setAmount(wallet.getAmount() + amount);
    }

    @Override
    public List<TransactionDto> checkDonationHistory(String campaignId) {
        List<TransactionDto> dtoList = new ArrayList<>();
        transactionRepository.findByCampaignId(campaignId).forEach(transaction -> dtoList.add(transaction.getDto()));
        return dtoList;
    }

    @Override
    public void withdraw(String fromWallet, String toWallet, TransactionDto dto) {

        //인출 가능한 금액인지 확인
        Wallet from = walletRepository.getWallet(fromWallet);
        Wallet to = walletRepository.getWallet(toWallet);

        if (from.getAmount() < dto.getAmount()) {
            throw new IllegalArgumentException("인출 금액이 너무 큽니다.");
        }
        //인출 : 거래 내역 등록, 켐패인 갱신
        TransactionDetail detail = new TransactionDetail();
        Transaction transaction = new Transaction(
                from,
                to,
                dto.getAmount(),
                dto.getType(),
                detail
        );
        detail.setTransaction(transaction);
        detail.setSender(dto.getSender());
        detail.setReceiver(dto.getReceiver());
        detail.setPurpose(dto.getPurpose());

        transactionRepository.save(transaction, detail);
    }
}
