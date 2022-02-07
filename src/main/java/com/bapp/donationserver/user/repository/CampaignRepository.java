package com.bapp.donationserver.user.repository;

import com.bapp.donationserver.data.CampaignInfo;

public interface CampaignRepository {

    void save(CampaignInfo campaignInfo);

    void update(String campaignId, CampaignInfo updateCampaignInfo);

    void delete(String campaignId);

    void findById(String campaignId);
}
