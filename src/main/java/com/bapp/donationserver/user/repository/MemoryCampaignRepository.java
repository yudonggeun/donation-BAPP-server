package com.bapp.donationserver.user.repository;

import com.bapp.donationserver.data.CampaignInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class MemoryCampaignRepository implements CampaignRepository{
    @Override
    public void save(CampaignInfo campaignInfo) {

    }

    @Override
    public void update(String campaignId, CampaignInfo updateCampaignInfo) {

    }

    @Override
    public void delete(String campaignId) {

    }

    @Override
    public void findById(String campaignId) {

    }
}
