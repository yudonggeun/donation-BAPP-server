package com.bapp.donationserver.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/user/basket")
@Slf4j
@RestController
public class UserBasketApiController {

    @GetMapping
    public String addBasket(@RequestParam String campaignId,
                            @RequestParam Long price){
        return "ok";
    }

    @PostMapping
    public String deleteBasket(@RequestParam String campaignId){
        return "ok";
    }
}
