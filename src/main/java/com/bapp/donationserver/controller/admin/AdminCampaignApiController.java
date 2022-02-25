package com.bapp.donationserver.controller.admin;

import com.bapp.donationserver.data.status.Status;
import com.bapp.donationserver.service.category.CategoryService;
import com.bapp.donationserver.data.type.MemberType;
import com.bapp.donationserver.data.SessionConst;
import com.bapp.donationserver.data.dto.CampaignFullDto;
import com.bapp.donationserver.data.dto.CampaignSearchConditionDto;
import com.bapp.donationserver.data.dto.MemberDto;
import com.bapp.donationserver.service.campaign.CampaignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/admin/campaign")
@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminCampaignApiController {

    private final CampaignService campaignService;

    @DeleteMapping("/{campaignId}")
    public Object deleteCampaign(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto,
                                 @PathVariable String campaignId){
        return Status.successStatus();
    }

    @GetMapping("/{campaignId}/limit")
    public Object changeLimitCampaign(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto,
                                      @PathVariable Long campaignId,
                                      @RequestParam("accept") Boolean isAccept){

        campaignService.acceptCampaign(campaignId, isAccept);
        return Status.successStatus();
    }

}
