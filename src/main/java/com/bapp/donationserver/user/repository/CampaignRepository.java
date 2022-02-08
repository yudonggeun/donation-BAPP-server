package com.bapp.donationserver.user.repository;

import com.bapp.donationserver.data.CampaignInfo;
import com.bapp.donationserver.data.CampaignSearchCondition;

import java.util.List;

public interface CampaignRepository {

    void save(CampaignInfo campaignInfo);

    void update(String campaignId, CampaignInfo updateCampaignInfo);

    void delete(String campaignId);

    CampaignInfo findById(String campaignId);

    List<CampaignInfo> findAll();

    List<CampaignInfo> findCampaignListByCondition(CampaignSearchCondition condition);
}
