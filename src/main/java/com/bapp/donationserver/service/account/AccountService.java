package com.bapp.donationserver.service.account;

import com.bapp.donationserver.data.Campaign;
import com.bapp.donationserver.data.Member;
import com.bapp.donationserver.data.dto.CampaignSimpleDto;
import com.bapp.donationserver.data.dto.MemberDto;

import java.util.List;

public interface AccountService {

    //개인 정보 조회
    Member getMember(String email);
    //개인 정보 수정
    void updateMember(String email, MemberDto updateData);
    //기부 내역 추가
    void addDonatedCampaign(String email, Campaign campaign);
    //자신의 기부 내역 조회
    List<CampaignSimpleDto> getDonationListByEmail(String email);
    //회원 가입
    boolean createNewMember(MemberDto newMemberInfo);
    //로그인
    Member login(String email, String password) throws Exception;
    //회원 탈퇴
    void deleteMember(Member member);
}
