package com.bapp.donationserver.user.service;

import com.bapp.donationserver.data.CampaignInfo;
import com.bapp.donationserver.data.Cart;
import com.bapp.donationserver.data.MemberInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {
    @Override
    public MemberInfo getMemberInformation(String memberId) {
        return null;
    }

    @Override
    public void updateMemberInformation(String memberId, MemberInfo updateMemberInformation) {

    }

    @Override
    public void putCampaignAtCart(String campaignId) {

    }

    @Override
    public void deleteCampaignAtCart(String campaignId) {

    }

    @Override
    public void clearCart(String campaignId) {

    }

    @Override
    public void buyDonationAtCart(Cart cart) {

    }

    @Override
    public List<CampaignInfo> checkMyDonationList(String memberId) {
        return null;
    }

}
