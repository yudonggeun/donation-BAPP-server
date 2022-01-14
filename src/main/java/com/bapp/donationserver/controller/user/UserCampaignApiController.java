package com.bapp.donationserver.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/user/campaign")
@Slf4j
@RestController
public class UserCampaignApiController {
    @GetMapping
    public String getCampaignList(){
        return "ok";
    }

    @PostMapping
    public String searchCampaign(@ModelAttribute SearchCondition searchCondition){
        return "ok";
    }

    @GetMapping("/{campaignId}")
    public String getCampaignDetail(@PathVariable Long campaignId){
        return "ok";
    }
}
