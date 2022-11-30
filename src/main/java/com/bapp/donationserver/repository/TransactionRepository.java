package com.bapp.donationserver.repository;

import com.bapp.donationserver.data.Transaction;
import com.bapp.donationserver.data.TransactionDetail;
import com.bapp.donationserver.data.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository {

    void save(String  fromPrivateKey, Transaction transaction, TransactionDetail detail);

    List<Transaction> findByCampaignId(Long campaignId);

    List<Transaction> findByWalletId(String walletId);
}
