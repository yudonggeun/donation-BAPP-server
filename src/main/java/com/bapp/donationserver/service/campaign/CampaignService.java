package com.bapp.donationserver.service.campaign;

import com.bapp.donationserver.entity.Campaign;
import com.bapp.donationserver.data.type.MemberType;
import com.bapp.donationserver.data.dto.CampaignFullDto;
import com.bapp.donationserver.data.dto.CampaignSearchConditionDto;
import com.bapp.donationserver.data.dto.CampaignSimpleDto;

import java.util.List;

public interface CampaignService {
    //캠패인 등록 요청
    void registerCampaign(CampaignFullDto campaignInfo);
    //켐패인 수정 요청
    void modifyCampaign(Long campaignId, CampaignFullDto campaignInfo);
    //캠페인 목록 조회 //켐페인 검색
    List<CampaignSimpleDto> getCampaignList(CampaignSearchConditionDto condition, MemberType memberType);
    //켐페인 상세 조회
    Campaign getCampaign(Long campaignId);
    //켐패인 승인 여부 변경 : 관리자 기능
    Boolean acceptCampaign(Long campaignId, Boolean status);
}
