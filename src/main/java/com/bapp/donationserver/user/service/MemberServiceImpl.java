package com.bapp.donationserver.user.service;

import com.bapp.donationserver.data.CampaignInfo;
import com.bapp.donationserver.data.Cart;
import com.bapp.donationserver.data.MemberInfo;
import com.bapp.donationserver.user.repository.CampaignRepository;
import com.bapp.donationserver.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final CampaignRepository campaignRepository;

    @Override
    public MemberInfo getMemberInformation(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public void updateMemberInformation(String email, MemberInfo updateMemberInformation) {
        memberRepository.update(email, updateMemberInformation);
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
