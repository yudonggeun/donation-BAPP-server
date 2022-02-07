package com.bapp.donationserver.charity.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/charity")
@Slf4j
@RestController
public class CharityApiController {
/*
    @GetMapping
    public String welcomeCharity(){
        return "ok";
    }*/

    /**
     * 클라이언트 전송 : 표지이미지, 상세이미지, 켐페인 제목, 재단 이름, 마감일, 현재 모금 금액, 목표 금액, 카테고리, 계획
     * 서버 응답 : success fail
     */
    @PostMapping
    public String registerCampaign() {
        return "ok";
    }

    /**
     * 클라이언트 전송 : 표지이미지, 상세이미지, 켐페인 제목, 재단 이름, 마감일, 현재 모금 금액, 목표 금액, 카테고리, 계획
     * 서버 응답 : success fail
     */
    @PostMapping("/{campaignId}")
    public String modifyCampaign(@PathVariable String campaignId) {
        return "ok";
    }

    /**
     * 클라이언트 전송 : 출금 금액
     * 서버 응답 : success fail
     */
    @PostMapping("/withdraw/{campaignId}")
    public String withdrawFromCampaign(@PathVariable String campaignId) {
        return "ok";
    }
}