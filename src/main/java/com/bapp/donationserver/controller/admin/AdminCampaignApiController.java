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
    private final CategoryService adminService;

    @GetMapping
    public Object getCampaignList(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto){
        campaignService.checkCampaignList(new CampaignSearchConditionDto(), memberDto.getMemberType());
        return Status.successStatus();
    }

    @PostMapping
    public Object registerCampaign(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto,
                                   @RequestBody CampaignFullDto dto){

        campaignService.registerCampaign(dto);
        return Status.successStatus();
    }

    @GetMapping("/{campaignId}")
    public CampaignFullDto inquiredCampaign(@PathVariable Long campaignId){
        return campaignService.checkDetailsOfCampaign(campaignId);
    }

    @PostMapping("/{campaignId}")
    public Object editCampaign(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto,
                               @PathVariable Long campaignId,
                               @RequestBody CampaignFullDto dto) {

        campaignService.modifyCampaign(campaignId, dto);
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
        campaignService.checkCampaignList(dto, MemberType.ADMIN);
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
