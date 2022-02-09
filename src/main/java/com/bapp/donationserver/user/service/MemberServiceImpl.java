package com.bapp.donationserver.user.service;

import com.bapp.donationserver.data.Campaign;
import com.bapp.donationserver.data.Cart;
import com.bapp.donationserver.data.Member;
import com.bapp.donationserver.repository.CampaignRepository;
import com.bapp.donationserver.repository.MemberRepository;
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
    public Member getMemberInformation(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public void updateMemberInformation(String email, Member updateMemberInformation) {
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
    public List<Campaign> checkMyDonationList(String memberId) {
        return null;
    }

}
