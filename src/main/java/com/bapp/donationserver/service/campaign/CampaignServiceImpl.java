package com.bapp.donationserver.service.campaign;

import com.bapp.donationserver.data.Campaign;
import com.bapp.donationserver.data.CampaignSearchCondition;
import com.bapp.donationserver.data.MemberType;
import com.bapp.donationserver.data.Wallet;
import com.bapp.donationserver.data.dto.CampaignFullDto;
import com.bapp.donationserver.data.dto.CampaignSearchConditionDto;
import com.bapp.donationserver.data.dto.CampaignSimpleDto;
import com.bapp.donationserver.repository.CampaignRepository;
import com.bapp.donationserver.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository campaignRepository;
    private final WalletRepository walletRepository;
    @Override
    public void registerCampaign(CampaignFullDto campaignInfo) {
        Campaign campaign = new Campaign(UUID.randomUUID().toString());
        campaign.setCampaignFullDto(campaignInfo);
        //지갑 생성 및 등록
        Wallet wallet = walletRepository.createWallet();
        campaign.setWallet(wallet);
        log.info("켐페인 등록={}", campaign);
        campaignRepository.save(campaign);
    }

    @Override
    public void modifyCampaign(String campaignId, CampaignFullDto campaignInfo) {
        Campaign campaign = campaignRepository.findById(campaignId);
        campaign.setCampaignFullDto(campaignInfo);
        campaignRepository.update(campaignId, campaign);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CampaignSimpleDto> checkCampaignList(CampaignSearchConditionDto dto, MemberType memberType) {
        //조건 설정
        CampaignSearchCondition condition = new CampaignSearchCondition();
        condition.setDto(dto);
        condition.setMemberType(memberType);

        //db 조회 및 dto 변환
        List<CampaignSimpleDto> dtoList = new ArrayList<>();
        campaignRepository.findCampaignListByCondition(condition)
                .forEach(campaign -> dtoList.add(campaign.getCampaignSimpleDto()));

        log.info("정상 반환 ={}", dtoList);
        return dtoList;
    }

    @Transactional(readOnly = true)
    @Override
    public CampaignFullDto checkDetailsOfCampaign(String campaignId) {
        return campaignRepository.findById(campaignId).getCampaignFullDto();
    }

    @Override
    public void acceptCampaign(String campaignId, Boolean status) {

    }

}
