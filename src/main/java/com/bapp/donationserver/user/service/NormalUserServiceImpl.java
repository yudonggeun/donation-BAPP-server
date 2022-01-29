package com.bapp.donationserver.user.service;

import com.bapp.donationserver.data.CampaignInfo;
import com.bapp.donationserver.data.CampaignSearchCondition;
import com.bapp.donationserver.data.MemberInfo;
import com.bapp.donationserver.data.Transaction;

import java.util.List;

public class NormalUserServiceImpl implements NormalUserService{
    @Override
    public void joinMemberShip(MemberInfo newMember) {

    }

    @Override
    public List<CampaignInfo> checkCampaignList(CampaignSearchCondition condition) {
        return null;
    }

    @Override
    public CampaignInfo checkDetailsOfCampaign(String CampaignId) {
        return null;
    }

    @Override
    public void pay(String CampaignId, Integer amount) {

    }

    @Override
    public List<Transaction> checkDonationHistory(String CampaignId) {
        return null;
    }
}
