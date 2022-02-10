package com.bapp.donationserver.repository;

import com.bapp.donationserver.data.Campaign;
import com.bapp.donationserver.data.CampaignSearchCondition;

import java.util.List;

public interface CampaignRepository {

    void save(Campaign campaign);

    void update(String campaignId, Campaign updateCampaign);

    void delete(String campaignId);

    Campaign findById(String campaignId);

    List<Campaign> findAll();

    List<Campaign> findCampaignListByCondition(CampaignSearchCondition condition);
}
