package com.bapp.donationserver.user.repository;

import com.bapp.donationserver.data.CampaignInfo;
import com.bapp.donationserver.data.CampaignSearchCondition;
import com.bapp.donationserver.data.Transaction;

import java.util.List;

public interface DonationTransactionRepository {

    void save(Transaction transaction);

    List<Transaction> findByCampaignId(String campaignId);

}
