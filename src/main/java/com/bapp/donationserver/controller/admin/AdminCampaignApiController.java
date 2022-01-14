package com.bapp.donationserver.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/admin/campaign")
@Slf4j
@RestController
public class AdminCampaignApiController {

    @GetMapping
    public String getCampaignList(){
        return null;
    }

    @PostMapping
    public String registerCampaign(){
        return null;
    }

    @GetMapping("/{campaignId}")
    public String inquiredCampaign(@PathVariable Long campaignId){
        return null;
    }

    @PostMapping("/{campaignId}")
    public String editCampaign(@PathVariable Long campaignId){
        return null;
    }

    @DeleteMapping("/{campaignId}")
    public String deleteCampaign(@PathVariable Long campaignId){
        return null;
    }

    @GetMapping("/request")
    public String requestCampaign(){
        return null;
    }

    @GetMapping("/{campaignId}/limit")
    public String limitCampaign(@PathVariable Long campaignId){
        return null;
    }

}
