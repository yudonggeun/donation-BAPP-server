package com.bapp.donationserver.controller.user;

import com.bapp.donationserver.data.SessionConst;
import com.bapp.donationserver.data.dto.CampaignSimpleDto;
import com.bapp.donationserver.data.dto.MemberDto;
import com.bapp.donationserver.data.dto.TransactionDto;
import com.bapp.donationserver.service.user.MemberService;
import com.bapp.donationserver.service.user.NormalUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/user/info")
@Slf4j
@RestController
@RequiredArgsConstructor
public class UserInfoApiController {

    private final NormalUserService normalUserService;
    private final MemberService memberService;

   /* @GetMapping
    public String checkDonationHistory(){
        return "ok";
    }*/

    /**
     * 클라이언트 : 사용자 id -> email로 수정
     * 응답 정보 : 표지이미지, 켐페인 제목, 재단 이름, 마감일, 현재 모금 금액, 목표 금액
     */
    @GetMapping//("/{email}") 헤더에 세션 쿠키 정보로 이메일 조회 가능
    public List<CampaignSimpleDto> myDonationList(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto) {

        return memberService.checkMyDonationList(memberDto.getEmail());
    }

    /**
     * 기부 내역(거래 내역) : 사용처, 출금(환전)액, 거래 내용, 출금 시간
     * 기부 내역 배열 전송
     */
    @GetMapping("/history")
    public List<TransactionDto> checkCampaignHistory(@RequestParam String campaignId) {

        return normalUserService.checkDonationHistory(campaignId);
    }
}
