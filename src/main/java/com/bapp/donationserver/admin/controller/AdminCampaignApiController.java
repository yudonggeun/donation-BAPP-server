package com.bapp.donationserver.admin.controller;

import com.bapp.donationserver.admin.service.AdminService;
import com.bapp.donationserver.charity.service.CharityService;
import com.bapp.donationserver.data.MemberType;
import com.bapp.donationserver.data.SessionConst;
import com.bapp.donationserver.data.dto.CampaignFullDto;
import com.bapp.donationserver.data.dto.CampaignSearchConditionDto;
import com.bapp.donationserver.data.dto.MemberDto;
import com.bapp.donationserver.user.service.NormalUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/admin/campaign")
@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminCampaignApiController {

    private final NormalUserService normalUserService;
    private final AdminService adminService;
    private final CharityService charityService;

    @GetMapping
    public String getCampaignList(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto){
        normalUserService.checkCampaignList(new CampaignSearchConditionDto(), memberDto.getMemberType());
        return "ok";
    }

    @PostMapping
    public String registerCampaign(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto,
                                   @RequestBody CampaignFullDto dto){

        charityService.registerCampaign(dto);
        return "ok";
    }

    @GetMapping("/{campaignId}")
    public CampaignFullDto inquiredCampaign(@PathVariable String campaignId){
        return normalUserService.checkDetailsOfCampaign(campaignId);
    }

    @PostMapping("/{campaignId}")
    public String editCampaign(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto,
                               @PathVariable String campaignId,
                               @RequestBody CampaignFullDto dto) {

        charityService.modifyCampaign(campaignId, dto);
        return "ok";
    }

    @DeleteMapping("/{campaignId}")
    public String deleteCampaign(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto,
                                 @PathVariable String campaignId){
        return "ok";
    }

    @GetMapping("/request")
    public String getRequestCampaignList(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto){

        CampaignSearchConditionDto dto = new CampaignSearchConditionDto();
        dto.setStartIndex(0);
        dto.setEndIndex(Integer.MAX_VALUE);
        normalUserService.checkCampaignList(dto, MemberType.ADMIN);
        return "ok";
    }

    @GetMapping("/{campaignId}/limit")
    public String changeLimitCampaign(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto,
                                      @PathVariable String campaignId,
                                      @RequestParam("accept") Boolean isAccept){

        adminService.changeCampaignAcceptTo(campaignId, isAccept);
        return "ok";
    }

}
