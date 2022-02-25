package com.bapp.donationserver.service.account;

import com.bapp.donationserver.data.dto.CampaignSimpleDto;
import com.bapp.donationserver.data.dto.MemberDto;

import java.util.List;

public interface AccountService {

    //개인 정보 조회
    MemberDto getMemberInformation(String email);
    //개인 정보 수정
    void updateMemberInformation(String email, MemberDto updateMemberInformation);
    //자신의 기부 내역 조회
    List<CampaignSimpleDto> checkMyDonationList(String email);
    //회원 가입
    void newMember(MemberDto newMemberInfo);
    //로그인
    MemberDto login(String email, String password);
    //회원 탈퇴
    void dropMember(String email);
}
