package com.bapp.donationserver.service.campaign;

import com.bapp.donationserver.data.MemberType;
import com.bapp.donationserver.data.dto.CampaignFullDto;
import com.bapp.donationserver.data.dto.CampaignSearchConditionDto;
import com.bapp.donationserver.data.dto.CampaignSimpleDto;
import com.bapp.donationserver.data.dto.TransactionDto;

import java.util.List;

public interface CampaignService {
    //캠패인 등록 요청
    void registerCampaign(CampaignFullDto campaignInfo);
    //켐패인 수정 요청
    void modifyCampaign(String campaignId, CampaignFullDto campaignInfo);
    //캠페인 목록 조회 //켐페인 검색
    List<CampaignSimpleDto> checkCampaignList(CampaignSearchConditionDto condition, MemberType memberType);
    //켐페인 상세 조회
    CampaignFullDto checkDetailsOfCampaign(String campaignId);
    //켐패인 승인 여부 변경 : 관리자 기능
    void acceptCampaign(String campaignId, Boolean status);
}
