package com.bapp.donationserver.charity.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/charity")
@Slf4j
@RestController
public class CharityApiController {

    @GetMapping
    public String welcomeCharity(){
        return "ok";
    }

    @PostMapping
    public String requestCampaign(){
        return "ok";
    }

    @PostMapping("/{campaignId}")
    public String withdrawFromCampaign(@PathVariable String campaignId){
        return "ok";
    }
}