package com.bapp.donationserver.repository.memory;

import com.bapp.donationserver.data.Transaction;
import com.bapp.donationserver.repository.DonationTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
@Slf4j
public class MemoryDonationTransactionRepository implements DonationTransactionRepository {

    private final Map<String, Transaction> db = new HashMap<>();

    @Override
    public void save(Transaction transaction) {
        db.put(UUID.randomUUID().toString(), transaction);
    }

    @Override
    public List<Transaction> findByCampaignId(String campaignId) {
        List<Transaction> list = new ArrayList<>();
        db.values().stream()
                .filter(transaction -> transaction.getCampaignId().equals(campaignId))
                .forEach(list::add);

        return list;
    }

    @PostConstruct
    public void init(){
        db.put("test", new Transaction(
                "test",
                "me",
                "you",
                10000L,
                20000L
        ));
    }
}
