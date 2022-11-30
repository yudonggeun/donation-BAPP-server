package com.bapp.donationserver.repository;

import com.bapp.donationserver.data.Transaction;
import com.bapp.donationserver.data.TransactionDetail;
import com.bapp.donationserver.data.Wallet;
import com.bapp.donationserver.repository.custom.CustomTransactionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository  extends JpaRepository<Transaction, String>, CustomTransactionRepository {

    @Query("select t from Transaction t join fetch t.detail where t.from = " +
            "(select c.wallet.id from Campaign c left join c.wallet where c.id = :campaignId)")
    List<Transaction> findByCampaignId(@Param("campaignId") Long campaignId);

    @Query("select t from Transaction t where t.from = :walletId or t.to = :walletId")
    List<Transaction> findByWalletId(@Param("walletId") String walletId);
}
