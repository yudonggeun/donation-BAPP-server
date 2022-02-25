package com.bapp.donationserver.service.transaction;

import com.bapp.donationserver.data.*;
import com.bapp.donationserver.data.dto.*;
import com.bapp.donationserver.repository.CampaignRepository;
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
    private final CampaignRepository campaignRepository;

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
    public void withdraw(String campaignId, String walletId, TransactionDto dto) {

        //인출 가능한 금액인지 확인
        Wallet wallet = walletRepository.getWallet(walletId);

        if (wallet.getAmount() < dto.getAmount()) {
            throw new IllegalArgumentException("인출 금액이 너무 큽니다.");
        }
        //인출 : 거래 내역 등록, 켐패인 갱신
        Transaction transaction = new Transaction(
                campaignId,
                dto.getSender(),
                dto.getReceiver(),
                dto.getAmount(),
                wallet.getAmount()
        );

        transactionRepository.save(transaction);
    }
}
