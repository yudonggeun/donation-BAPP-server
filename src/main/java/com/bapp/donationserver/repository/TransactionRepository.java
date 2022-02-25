package com.bapp.donationserver.repository;

import com.bapp.donationserver.data.Transaction;

import java.util.List;

public interface TransactionRepository {

    void save(Transaction transaction);

    List<Transaction> findByCampaignId(String campaignId);

}
