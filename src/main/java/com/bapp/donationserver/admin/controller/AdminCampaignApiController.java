package com.bapp.donationserver.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/admin/campaign")
@Slf4j
@RestController
public class AdminCampaignApiController {

    @GetMapping
    public String getCampaignList(){
        return "ok";
    }

    @PostMapping
    public String registerCampaign(){
        return "ok";
    }

    @GetMapping("/{campaignId}")
    public String inquiredCampaign(@PathVariable String campaignId){
        return "ok";
    }

    @PostMapping("/{campaignId}")
    public String editCampaign(@PathVariable String campaignId){
        return "ok";
    }

    @DeleteMapping("/{campaignId}")
    public String deleteCampaign(@PathVariable String campaignId){
        return "ok";
    }

    @GetMapping("/request")
    public String requestCampaign(){
        return "ok";
    }

    @GetMapping("/{campaignId}/limit")
    public String limitCampaign(@PathVariable String campaignId){
        return "ok";
    }

}
