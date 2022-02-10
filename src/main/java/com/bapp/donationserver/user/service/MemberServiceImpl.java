package com.bapp.donationserver.user.service;

import com.bapp.donationserver.data.Cart;
import com.bapp.donationserver.data.dto.CampaignSimpleDto;
import com.bapp.donationserver.data.dto.MemberDto;
import com.bapp.donationserver.repository.CampaignRepository;
import com.bapp.donationserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final CampaignRepository campaignRepository;

    @Override
    public MemberDto getMemberInformation(String email) {
        return memberRepository.findByEmail(email).getMyPageDto();
    }

    @Override
    public void updateMemberInformation(String email, MemberDto updateMemberInformation) {
        memberRepository.update(email, updateMemberInformation);
    }

    @Override
    public List<CampaignSimpleDto> checkMyDonationList(String email) {
        List<CampaignSimpleDto> donationList = new ArrayList<>();
        memberRepository.findByEmail(email)
                .getDonatedCampaigns()
                .forEach(campaignInfo -> donationList.add(campaignInfo.getCampaignSimpleDto()));
        return donationList;
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

}
