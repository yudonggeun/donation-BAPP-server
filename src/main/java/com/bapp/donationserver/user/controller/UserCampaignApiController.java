package com.bapp.donationserver.user.controller;

import com.bapp.donationserver.user.SearchCondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/user/campaign")
@Slf4j
@RestController
public class UserCampaignApiController {
    /**
     * 캠패인 정보 : 표지이미지, 켐페인 제목, 재단 이름, 마감일, 현재 모금 금액, 목표 금액
     *
     */
    @GetMapping
    public String getCampaignList(){
        return "ok";
    }

    /**
     * 검색 조건 : 페이지 정보, 단채명, 제목, 카테고리 중복, 관심
     *
     */
    @PostMapping
    public String searchCampaign(@ModelAttribute SearchCondition searchCondition){
        return "ok";
    }

    /**
     * 서버 전송 : 표지이미지, 상세이미지, 캠페인 제목, 재단 이름, 마감일, 현재 모금 금액, 목표 금액, 카테고리, 계획
     *
     */
    @GetMapping("/{campaignId}")
    public String getCampaignDetail(@PathVariable String campaignId){
        return "ok";
    }
}
