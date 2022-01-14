package com.bapp.donationserver.controller.user;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/user")
@Slf4j
@RestController
public class UserApiController {

    @GetMapping
    public String getMyPage(){
        return "ok";
    }

    @PostMapping
    public String editMyPage(){
        return "ok";
    }

    @PutMapping
    public String newUser(@ModelAttribute NewUserForm newUserForm){
        return "ok";
    }

    @PostMapping("/login")
    public String login(@RequestParam String id,
                        @RequestParam String password){
        return "ok";
    }

    @PostMapping("/pay")
    public String payComplete(){
        return "ok";
    }
}
