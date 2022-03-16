package com.bapp.donationserver.repository;

import com.bapp.donationserver.data.Campaign;
import com.bapp.donationserver.data.CampaignSearchCondition;

import java.util.List;

public interface CampaignRepository {

    void save(Campaign campaign);

    void update(Campaign campaign);

    void delete(Campaign campaign);

    Campaign findById(Long campaignId);

    List<Campaign> findAll();

    List<Campaign> findCampaignListByCondition(CampaignSearchCondition condition);
}
