package com.bapp.donationserver.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/user/info")
@Slf4j
@RestController
public class UserInfoApiController {
   /* @GetMapping
    public String checkDonationHistory(){
        return "ok";
    }*/

    /**
     * 클라이언트 : 사용자 id
     * 응답 정보 : 표지이미지, 켐페인 제목, 재단 이름, 마감일, 현재 모금 금액, 목표 금액
     */
    @GetMapping("/{userId}")
    public String myDonationList(@PathVariable Long userId){
        return "ok";
    }

    /**
     * 기부 내역(거래 내역) : 사용처, 출금(환전)액, 거래 내용, 출금 시간
     * 기부 내역 배열 전송
     */
    @GetMapping("/{campaignId}")
    public String checkCampaignHistory(@PathVariable String campaignId){
        return "ok";
    }
}
