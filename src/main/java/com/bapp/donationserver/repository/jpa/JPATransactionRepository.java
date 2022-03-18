package com.bapp.donationserver.repository.jpa;

import com.bapp.donationserver.data.Campaign;
import com.bapp.donationserver.data.Transaction;
import com.bapp.donationserver.data.TransactionDetail;
import com.bapp.donationserver.repository.TransactionRepository;
import com.bapp.donationserver.service.blockchain.BlockChainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Repository
@Slf4j
@Primary
@RequiredArgsConstructor
public class JPATransactionRepository implements TransactionRepository {

    @PersistenceContext
    private final EntityManager em;
    private final BlockChainService blockChainService;

    @Override
    public void save(Transaction transaction, TransactionDetail detail) {
        String id = blockChainService.putTransaction(transaction, detail);
        transaction.setId(id);

        em.persist(transaction);
        if(detail != null)
            em.persist(detail);
    }

    @Override
    public List<Transaction> findByCampaignId(Long campaignId) {
        String query = "select t from Transaction t join fetch t.detail where t.from = " +
                "(select c.wallet.id from Campaign c left join c.wallet where c.id = :campaignId)";

        return em.createQuery(query, Transaction.class)
                .setParameter("campaignId", campaignId)
                .getResultList();
//        return em.find(Campaign.class, campaignId).getWallet().getTransactions();
    }
}
