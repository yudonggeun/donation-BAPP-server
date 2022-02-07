package com.bapp.donationserver.user.service;

import com.bapp.donationserver.data.CampaignInfo;
import com.bapp.donationserver.data.CampaignSearchCondition;
import com.bapp.donationserver.data.MemberInfo;
import com.bapp.donationserver.data.Transaction;
import com.bapp.donationserver.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NormalUserServiceImpl implements NormalUserService{

    private final MemberRepository memberRepository;

    @Override
    public void joinMember(MemberInfo newMember) {
        log.info("서비스 호출");
        memberRepository.save(newMember);
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
