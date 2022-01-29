package com.bapp.donationserver.user.controller;

import com.bapp.donationserver.user.SearchCondition;
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
    public String getCampaignDetail(@PathVariable String campaignId){
        return "ok";
    }
}
