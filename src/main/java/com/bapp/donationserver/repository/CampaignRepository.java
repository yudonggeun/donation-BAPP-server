package com.bapp.donationserver.repository;

import com.bapp.donationserver.data.Campaign;
import com.bapp.donationserver.data.CampaignSearchCondition;

import java.util.List;

public interface CampaignRepository {

    void save(Campaign campaign);

    void update(Long campaignId, Campaign updateCampaign);

    void delete(Long campaignId);

    Campaign findById(Long campaignId);

    List<Campaign> findAll();

    List<Campaign> findCampaignListByCondition(CampaignSearchCondition condition);
}
