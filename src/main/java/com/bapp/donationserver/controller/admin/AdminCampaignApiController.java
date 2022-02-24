package com.bapp.donationserver.controller.admin;

import com.bapp.donationserver.data.status.Status;
import com.bapp.donationserver.service.admin.AdminService;
import com.bapp.donationserver.service.charity.CharityService;
import com.bapp.donationserver.data.MemberType;
import com.bapp.donationserver.data.SessionConst;
import com.bapp.donationserver.data.dto.CampaignFullDto;
import com.bapp.donationserver.data.dto.CampaignSearchConditionDto;
import com.bapp.donationserver.data.dto.MemberDto;
import com.bapp.donationserver.service.user.NormalUserService;
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
    public Object getCampaignList(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto){
        normalUserService.checkCampaignList(new CampaignSearchConditionDto(), memberDto.getMemberType());
        return Status.successStatus();
    }

    @PostMapping
    public Object registerCampaign(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto,
                                   @RequestBody CampaignFullDto dto){

        charityService.registerCampaign(dto);
        return Status.successStatus();
    }

    @GetMapping("/{campaignId}")
    public CampaignFullDto inquiredCampaign(@PathVariable String campaignId){
        return normalUserService.checkDetailsOfCampaign(campaignId);
    }

    @PostMapping("/{campaignId}")
    public Object editCampaign(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto,
                               @PathVariable String campaignId,
                               @RequestBody CampaignFullDto dto) {

        charityService.modifyCampaign(campaignId, dto);
        return Status.successStatus();
    }

    @DeleteMapping("/{campaignId}")
    public Object deleteCampaign(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto,
                                 @PathVariable String campaignId){
        return Status.successStatus();
    }

    @GetMapping("/request")
    public Object getRequestCampaignList(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto){

        CampaignSearchConditionDto dto = new CampaignSearchConditionDto();
        dto.setStartIndex(0);
        dto.setEndIndex(Integer.MAX_VALUE);
        normalUserService.checkCampaignList(dto, MemberType.ADMIN);
        return Status.successStatus();
    }

    @GetMapping("/{campaignId}/limit")
    public Object changeLimitCampaign(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto,
                                      @PathVariable String campaignId,
                                      @RequestParam("accept") Boolean isAccept){

        adminService.changeCampaignAcceptTo(campaignId, isAccept);
        return Status.successStatus();
    }

}
