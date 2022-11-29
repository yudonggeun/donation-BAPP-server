package com.bapp.donationserver.repository.custom;

import com.bapp.donationserver.data.Campaign;
import com.bapp.donationserver.data.CampaignSearchCondition;

import java.util.List;

public interface CustomCampaignRepository {

    void update(Campaign campaign, List<String> categories);

    List<Campaign> findCampaignListByCondition(CampaignSearchCondition condition);
}
