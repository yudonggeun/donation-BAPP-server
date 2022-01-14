package com.bapp.donationserver.controller.charity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/charity")
@Slf4j
@RestController
public class CharityApiController {

    @GetMapping
    public String welcomeCharity(){
        return "ok";
    }

    @PostMapping("/request")
    public String requestCampaign(){
        return "ok";
    }

    @PostMapping("/withdraw")
    public String withdrawFromCampaign(){
        return "ok";
    }
}