package com.bapp.donationserver.user.service;

import com.bapp.donationserver.data.*;
import com.bapp.donationserver.data.dto.CampaignFullDto;
import com.bapp.donationserver.data.dto.CampaignSimpleDto;
import com.bapp.donationserver.data.dto.MemberDto;
import com.bapp.donationserver.data.dto.TransactionDto;

import java.util.List;

public interface NormalUserService {
    //회원 가입
    void joinMember(MemberDto newMemberInfo);
    //캠페인 목록 조회 //켐페인 검색
    List<CampaignSimpleDto> checkCampaignList(CampaignSearchCondition condition);
    //켐페인 상세 조회
    CampaignFullDto checkDetailsOfCampaign(String campaignId);
    //하나의 상품 결제
    void pay(String memberEmail, Long amount);

    //기부 사용 내역 조회
    List<TransactionDto> checkDonationHistory(String campaignId);
}
