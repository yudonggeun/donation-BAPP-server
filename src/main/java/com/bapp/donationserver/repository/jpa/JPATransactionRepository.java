package com.bapp.donationserver.repository.jpa;

import com.bapp.donationserver.data.Campaign;
import com.bapp.donationserver.data.Transaction;
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
    public void save(Transaction transaction) {
        log.info("거래 저장 transaction={}", transaction);
        em.persist(transaction);
    }

    @Override
    public List<Transaction> findByCampaignId(String campaignId) {
        return em.find(Campaign.class, campaignId).getTransactions();
    }
}
