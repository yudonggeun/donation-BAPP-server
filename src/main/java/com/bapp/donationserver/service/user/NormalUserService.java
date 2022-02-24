package com.bapp.donationserver.service.user;

import com.bapp.donationserver.data.*;
import com.bapp.donationserver.data.dto.*;

import java.util.List;

public interface NormalUserService {
    //회원 가입
    void newMember(MemberDto newMemberInfo);
    //로그인
    MemberDto login(String email, String password);
    //캠페인 목록 조회 //켐페인 검색
    List<CampaignSimpleDto> checkCampaignList(CampaignSearchConditionDto condition, MemberType memberType);
    //켐페인 상세 조회
    CampaignFullDto checkDetailsOfCampaign(String campaignId);
    //하나의 상품 결제
    void pay(String memberEmail, Long amount);
    //기부 사용 내역 조회
    List<TransactionDto> checkDonationHistory(String campaignId);
}
