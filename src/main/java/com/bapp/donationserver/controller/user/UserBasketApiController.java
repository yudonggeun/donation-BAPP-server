package com.bapp.donationserver.controller.user;

import com.bapp.donationserver.data.status.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/user/basket")
@Slf4j
@RestController
public class UserBasketApiController {

    @GetMapping
    public Object addBasket(@RequestParam String campaignId,
                            @RequestParam Long price){
        return Status.successStatus();
    }

    @PostMapping
    public Object deleteBasket(@RequestParam String campaignId){
        return Status.successStatus();
    }
}
