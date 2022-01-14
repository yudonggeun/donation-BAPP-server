package com.bapp.donationserver.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/admin")
@Slf4j
@RestController
public class AdminApiController {
    @GetMapping
    public String welcomeAdmin(){
        return "ok";
    }

}
