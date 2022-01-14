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
        return null;
    }

    @PostMapping
    public String editMyPage(){
        return null;
    }

    @PutMapping
    public String newUser(@ModelAttribute NewUserForm newUserForm){
        return null;
    }

    @PostMapping("/login")
    public String login(@RequestParam String id,
                        @RequestParam String password){
        return null;
    }

    @PostMapping("/pay")
    public String payComplete(){
        return null;
    }
}
