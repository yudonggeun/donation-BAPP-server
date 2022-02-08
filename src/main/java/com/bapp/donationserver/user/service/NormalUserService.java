package com.bapp.donationserver.user.service;

import com.bapp.donationserver.data.*;

import java.util.List;

public interface NormalUserService {
    //회원 가입
    void joinMember(MemberInfo newMember);
    //캠페인 목록 조회 //켐페인 검색
    List<CampaignInfo> checkCampaignList(CampaignSearchCondition condition);
    //켐페인 상세 조회
    CampaignInfo checkDetailsOfCampaign(String campaignId);
    //하나의 상품 결제
    void pay(String CampaignId, Integer amount);
    //기부 사용 내역 조회
    List<Transaction> checkDonationHistory(String campaignId);
}
