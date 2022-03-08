package com.bapp.donationserver.controller.newUser;

import com.bapp.donationserver.data.Member;
import com.bapp.donationserver.data.SessionConst;
import com.bapp.donationserver.data.dto.*;
import com.bapp.donationserver.service.campaign.CampaignService;
import com.bapp.donationserver.service.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/new/campaign")
@Slf4j
@RestController
@RequiredArgsConstructor
public class NewCampaignController {

    private final CampaignService campaignService;
    private final TransactionService transactionService;
    /**
     * 검색 조건 : 페이지 정보, 단채명, 제목, 카테고리 중복, 관심
     */
    @PostMapping
    public List<CampaignSimpleDto> userSearchCampaign(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                                                      @RequestBody CampaignSearchConditionDto searchCondition) {

        return campaignService.checkCampaignList(searchCondition, member.getMemberType());
    }

    /**
     * 서버 전송 : 표지이미지, 상세이미지, 캠페인 제목, 재단 이름, 마감일, 현재 모금 금액, 목표 금액, 카테고리, 계획
     */
    @GetMapping("/{campaignId}")
    public CampaignFullDto getCampaignDetail(@PathVariable Long campaignId) {
        return campaignService.checkDetailsOfCampaign(campaignId).getCampaignFullDto();
    }


    /**
     * 기부 내역(거래 내역) : 사용처, 출금(환전)액, 거래 내용, 출금 시간
     * 기부 내역 배열 전송
     */
    @GetMapping("/history")
    public List<TransactionDto> checkCampaignHistory(@RequestParam String campaignId) {
        return transactionService.checkDonationHistory(campaignId);
    }
}
