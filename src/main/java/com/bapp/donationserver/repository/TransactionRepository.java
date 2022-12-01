package com.bapp.donationserver.repository;

import com.bapp.donationserver.entity.Transaction;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository  extends JpaRepository<Transaction, String> {


    @EntityGraph(attributePaths = {"detail"})
    @Query("select t from Transaction t where t.from = " +
            "(select c.wallet.id from Campaign c left join c.wallet where c.id = :campaignId)")
    List<Transaction> findWithdrawListByCampaignId(@Param("campaignId") Long campaignId);

    @Query("select t from Transaction t where t.from = :walletId or t.to = :walletId")
    List<Transaction> findByWalletId(@Param("walletId") String walletId);
}
