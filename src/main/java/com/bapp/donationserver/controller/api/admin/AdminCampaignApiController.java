package com.bapp.donationserver.controller.api.admin;

import com.bapp.donationserver.data.status.Return;
import com.bapp.donationserver.service.campaign.CampaignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminCampaignApiController {

    private final CampaignService campaignService;

    @GetMapping("/limit/{campaignId}")
    public Return changeLimitCampaign(@PathVariable Long campaignId,
                                      @RequestParam("accept") Boolean isAccept) {
        campaignService.acceptCampaign(campaignId, isAccept);
        return Return.successStatus();
    }
}
