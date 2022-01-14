package com.bapp.donationserver.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/admin")
@Slf4j
public class AdminApiController {
    @GetMapping
    public String welcomeAdmin(){
        return null;
    }

}
