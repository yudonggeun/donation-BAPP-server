package com.bapp.donationserver.service.charity;

import com.bapp.donationserver.data.Campaign;
import com.bapp.donationserver.data.dto.CampaignFullDto;
import com.bapp.donationserver.data.dto.CampaignSimpleDto;
import com.bapp.donationserver.data.dto.TransactionDto;

import java.util.List;

public interface CharityService {
    //캠패인 등록 요청
    void registerCampaign(CampaignFullDto campaignInfo);
    //켐패인 수정 요청
    void modifyCampaign(String campaignId, CampaignFullDto campaignInfo);
    //캠패인 자금 출금
    void withdraw(String campaignId, TransactionDto dto);
    //캠패인 리스트 조회
    List<CampaignSimpleDto> checkCampaignList(String memberId);
}
