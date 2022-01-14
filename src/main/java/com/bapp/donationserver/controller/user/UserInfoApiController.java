package com.bapp.donationserver.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/user/info")
@Slf4j
@RestController
public class UserInfoApiController {
    @GetMapping
    public String checkDonationHistory(){
        return null;
    }

    @GetMapping("{userId}")
    public String myDonationList(@PathVariable Long userId){
        return null;
    }

    @GetMapping("{campaignId}")
    public String checkCampaignHistory(@PathVariable Long campaignId){
        return null;
    }
}
