package com.bapp.donationserver.charity.service;

import com.bapp.donationserver.data.CampaignInfo;

import java.util.List;

public interface CharityService {
    //캠패인 등록 요청
    void requestToRegisterCampaign(CampaignInfo campaignInfo);
    //캠패인 자금 출금
    void withdraw(String CampaignId, Integer money);
    //캠패인 리스트 조회
    List<CampaignInfo> checkCampaignList(String memberId);
}
