package com.bapp.donationserver.controller.api.charity;

import com.bapp.donationserver.data.Campaign;
import com.bapp.donationserver.data.Member;
import com.bapp.donationserver.data.consts.CampaignConst;
import com.bapp.donationserver.data.dto.MemberDto;
import com.bapp.donationserver.data.dto.TransactionDetailDto;
import com.bapp.donationserver.data.status.Status;
import com.bapp.donationserver.data.consts.SessionConst;
import com.bapp.donationserver.data.dto.CampaignFullDto;
import com.bapp.donationserver.data.dto.TransactionDto;
import com.bapp.donationserver.service.campaign.CampaignService;
import com.bapp.donationserver.service.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequestMapping("/api/charity")
@Slf4j
@RestController
@RequiredArgsConstructor
public class CharityApiController {

    private final CampaignService campaignService;
    private final TransactionService transactionService;

    /**
     * 클라이언트 전송 : 표지이미지, 상세이미지, 켐페인 제목, 재단 이름, 마감일, 현재 모금 금액, 목표 금액, 카테고리, 계획
     * 서버 응답 : success fail
     */
    @PostMapping
    public Status registerCampaign(@RequestBody CampaignFullDto dto) {
        campaignService.registerCampaign(dto);
        return Status.successStatus();
    }

    /**
     * 클라이언트 전송 : 표지이미지, 상세이미지, 켐페인 제목, 재단 이름, 마감일, 현재 모금 금액, 목표 금액, 카테고리, 계획
     * 서버 응답 : success fail
     */
    @PostMapping("/{campaignId}")
    public Object modifyCampaign(@PathVariable Long campaignId, @RequestBody CampaignFullDto dto) {
        campaignService.modifyCampaign(campaignId, dto);
        return Status.successStatus();
    }

    //TODO
    @DeleteMapping("/{campaignId}")
    public Object deleteCampaign(@PathVariable String campaignId) {
        return Status.successStatus();
    }

    /**
     * 클라이언트 전송 : 출금 금액
     * 서버 응답 : success fail
     */
    @PostMapping("/withdraw/{campaignId}")
    public Object withdrawFromCampaign(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto,
                                       @PathVariable Long campaignId,
                                       @RequestBody TransactionDetailDto dto) {
        Campaign campaign = campaignService.getDetailsOfCampaign(campaignId);
        transactionService.withdraw(campaign, memberDto, dto);
        return Status.successStatus();
    }
}