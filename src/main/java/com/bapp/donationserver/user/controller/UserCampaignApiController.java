package com.bapp.donationserver.user.controller;

import com.bapp.donationserver.data.Campaign;
import com.bapp.donationserver.data.CampaignSearchCondition;
import com.bapp.donationserver.data.dto.CampaignFullDto;
import com.bapp.donationserver.data.dto.CampaignSimpleDto;
import com.bapp.donationserver.user.service.MemberService;
import com.bapp.donationserver.user.service.NormalUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/user/campaign")
@Slf4j
@RestController
@RequiredArgsConstructor
public class UserCampaignApiController {

    private final MemberService memberService;
    private final NormalUserService normalUserService;


    /**
     * 캠패인 정보 : 표지이미지, 켐페인 제목, 재단 이름, 마감일, 현재 모금 금액, 목표 금액
     */
    @GetMapping
    public List<CampaignSimpleDto> getCampaignList() {

        return searchCampaign(new CampaignSearchCondition());
    }

    /**
     * 검색 조건 : 페이지 정보, 단채명, 제목, 카테고리 중복, 관심
     */
    @PostMapping
    public List<CampaignSimpleDto> searchCampaign(@RequestBody CampaignSearchCondition searchCondition) {

        return normalUserService.checkCampaignList(searchCondition);
    }

    /**
     * 서버 전송 : 표지이미지, 상세이미지, 캠페인 제목, 재단 이름, 마감일, 현재 모금 금액, 목표 금액, 카테고리, 계획
     */
    @GetMapping("/{campaignId}")
    public CampaignFullDto getCampaignDetail(@PathVariable String campaignId) {
        return normalUserService.checkDetailsOfCampaign(campaignId);
    }

}
