package com.bapp.donationserver.charity.controller;

import com.bapp.donationserver.charity.service.CharityService;
import com.bapp.donationserver.data.SessionConst;
import com.bapp.donationserver.data.dto.CampaignFullDto;
import com.bapp.donationserver.data.dto.MemberDto;
import com.bapp.donationserver.data.dto.TransactionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/charity")
@Slf4j
@RestController
@RequiredArgsConstructor
public class CharityApiController {

    private final CharityService charityService;

    /**
     * 클라이언트 전송 : 표지이미지, 상세이미지, 켐페인 제목, 재단 이름, 마감일, 현재 모금 금액, 목표 금액, 카테고리, 계획
     * 서버 응답 : success fail
     */
    @PostMapping
    public String registerCampaign(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto,
                                       @RequestBody CampaignFullDto dto) {

        log.info("CampaignFullDto={}",dto);

        if(memberDto == null){
            return "fail";
        }

        charityService.registerCampaign(dto);
        return "success";
    }

    /**
     * 클라이언트 전송 : 표지이미지, 상세이미지, 켐페인 제목, 재단 이름, 마감일, 현재 모금 금액, 목표 금액, 카테고리, 계획
     * 서버 응답 : success fail
     */
    @PostMapping("/{campaignId}")
    public String modifyCampaign(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto,
                                 @PathVariable String campaignId, @RequestBody CampaignFullDto dto) {

        if(memberDto == null){
            return "fail";
        }

        charityService.modifyCampaign(campaignId, dto);
        return "success";
    }

    /**
     * 클라이언트 전송 : 출금 금액
     * 서버 응답 : success fail
     */
    @PostMapping("/withdraw/{campaignId}")
    public String withdrawFromCampaign(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto,
                                       @PathVariable String campaignId,
                                       @RequestBody TransactionDto dto) {

        if(memberDto == null){
            return "fail";
        }

        charityService.withdraw(campaignId, dto);
        return "success";
    }
}