package com.bapp.donationserver.user.service;

import com.bapp.donationserver.data.*;
import com.bapp.donationserver.data.dto.CampaignFullDto;
import com.bapp.donationserver.data.dto.CampaignSimpleDto;
import com.bapp.donationserver.data.dto.MemberDto;
import com.bapp.donationserver.data.dto.TransactionDto;
import com.bapp.donationserver.repository.CampaignRepository;
import com.bapp.donationserver.repository.DonationTransactionRepository;
import com.bapp.donationserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NormalUserServiceImpl implements NormalUserService {

    private final MemberRepository memberRepository;
    private final CampaignRepository campaignRepository;
    private final DonationTransactionRepository transactionRepository;

    @Override
    public void joinMember(MemberDto newMember) {
        log.info("서비스 호출");
        memberRepository.save(newMember);
    }

    @Override
    public List<CampaignSimpleDto> checkCampaignList(CampaignSearchCondition condition) {
        List<Campaign> campaignInfos = campaignRepository.findCampaignListByCondition(condition);

        List<CampaignSimpleDto> dtoList = new ArrayList<>();

        if (condition == null)
            condition = new CampaignSearchCondition();

        if (campaignInfos == null || campaignInfos.size() - 1 < condition.getStartIndex()) {
            log.info("검색 조건에 부합하는 캠페인이 없습니다.");
            return dtoList;
        }

        campaignInfos.subList(condition.getStartIndex(),
                (campaignInfos.size() <= condition.getEndIndex() ? campaignInfos.size() : condition.getEndIndex() + 1)
        ).forEach(campaign -> dtoList.add(campaign.getCampaignSimpleDto()));

        log.info("정상 반환 ={}", dtoList);
        return dtoList;
    }

    @Override
    public CampaignFullDto checkDetailsOfCampaign(String campaignId) {
        return campaignRepository.findById(campaignId).getCampaignFullDto();
    }

    @Override
    public void pay(String memberEmail, Long amount) {
        log.info("결제 완료");
        Member member = memberRepository.findByEmail(memberEmail);

        Wallet wallet = member.getWallet();
        wallet.setAmount(wallet.getAmount() + amount);
    }

    @Override
    public List<TransactionDto> checkDonationHistory(String campaignId) {
        List<TransactionDto> dtoList = new ArrayList<>();
        transactionRepository.findByCampaignId(campaignId).forEach(transaction -> dtoList.add(transaction.getDto()));
        return dtoList;
    }
}
