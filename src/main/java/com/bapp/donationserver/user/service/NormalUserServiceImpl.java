package com.bapp.donationserver.user.service;

import com.bapp.donationserver.data.Campaign;
import com.bapp.donationserver.data.CampaignSearchCondition;
import com.bapp.donationserver.data.Member;
import com.bapp.donationserver.data.Transaction;
import com.bapp.donationserver.repository.CampaignRepository;
import com.bapp.donationserver.repository.DonationTransactionRepository;
import com.bapp.donationserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NormalUserServiceImpl implements NormalUserService{

    private final MemberRepository memberRepository;
    private final CampaignRepository campaignRepository;
    private final DonationTransactionRepository transactionRepository;

    @Override
    public void joinMember(Member newMember) {
        log.info("서비스 호출");
        memberRepository.save(newMember);
    }

    @Override
    public List<Campaign> checkCampaignList(CampaignSearchCondition condition) {
        List<Campaign> campaignInfos = campaignRepository.findCampaignListByCondition(condition);

        if(condition == null)
            return campaignInfos;
        if(campaignInfos.size()-1 < condition.getStartIndex())
            return null;
        if(campaignInfos.size()-1 < condition.getEndIndex())
            return campaignInfos.subList(condition.getStartIndex(), campaignInfos.size());
        return campaignInfos.subList(condition.getStartIndex(), condition.getEndIndex());
    }

    @Override
    public Campaign checkDetailsOfCampaign(String campaignId) {
        return campaignRepository.findById(campaignId);
    }

    @Override
    public void pay(String CampaignId, Integer amount) {
        log.info("결제 완료");
    }

    @Override
    public List<Transaction> checkDonationHistory(String campaignId) {
        return transactionRepository.findByCampaignId(campaignId);
    }
}
