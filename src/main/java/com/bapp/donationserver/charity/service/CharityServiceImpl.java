package com.bapp.donationserver.charity.service;

import com.bapp.donationserver.data.Campaign;
import com.bapp.donationserver.data.Member;
import com.bapp.donationserver.data.Transaction;
import com.bapp.donationserver.data.dto.CampaignFullDto;
import com.bapp.donationserver.data.dto.CampaignSimpleDto;
import com.bapp.donationserver.data.dto.TransactionDto;
import com.bapp.donationserver.repository.CampaignRepository;
import com.bapp.donationserver.repository.DonationTransactionRepository;
import com.bapp.donationserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CharityServiceImpl implements CharityService {

    private final CampaignRepository campaignRepository;
    private final MemberRepository memberRepository;
    private final DonationTransactionRepository transactionRepository;

    @Override
    public void registerCampaign(CampaignFullDto campaignInfo) {
        Campaign campaign = new Campaign(UUID.randomUUID().toString());
        campaign.setCampaignFullDto(campaignInfo);
        campaignRepository.save(campaign);
    }

    @Override
    public void modifyCampaign(String campaignId, CampaignFullDto campaignInfo) {
        Campaign campaign = campaignRepository.findById(campaignId);
        campaign.setCampaignFullDto(campaignInfo);
        campaignRepository.update(campaignId, campaign);
    }

    @Override
    public void withdraw(String campaignId, TransactionDto dto) {

        //인출 가능한 금액인지 확인
        Campaign campaign = campaignRepository.findById(campaignId);

        if (campaign.getCurrentAmount() < dto.getAmount()) {
            throw new IllegalArgumentException("인출 금액이 너무 큽니다.");
        }
        //인출 : 거래 내역 등록, 켐패인 갱신
        campaign.setCurrentAmount(campaign.getCurrentAmount() - dto.getAmount());

        Transaction transaction = new Transaction(
                campaignId,
                dto.getSender(),
                dto.getReceiver(),
                dto.getAmount(),
                campaign.getCurrentAmount()
        );



        campaignRepository.update(campaignId, campaign);
        transactionRepository.save(transaction);
    }

    @Override
    public List<CampaignSimpleDto> checkCampaignList(String email) {
        Member member = memberRepository.findByEmail(email);
        List<CampaignSimpleDto> dtoList = new ArrayList<>();
        member.getInterestCampaigns().forEach(campaign -> dtoList.add(campaign.getCampaignSimpleDto()));
        return dtoList;
    }
}
