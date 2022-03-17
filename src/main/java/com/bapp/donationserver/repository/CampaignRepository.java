package com.bapp.donationserver.repository;

import com.bapp.donationserver.data.Campaign;
import com.bapp.donationserver.data.CampaignSearchCondition;

import java.util.List;

public interface CampaignRepository {

    void save(Campaign campaign, List<String> categories);

    void update(Campaign campaign, List<String> categories);

    void delete(Campaign campaign);

    Campaign findById(Long campaignId);

    List<Campaign> findAll();

    List<Campaign> findCampaignListByCondition(CampaignSearchCondition condition);
}
