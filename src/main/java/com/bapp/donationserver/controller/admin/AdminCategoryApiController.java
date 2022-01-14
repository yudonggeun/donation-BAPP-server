package com.bapp.donationserver.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/admin/category")
@Slf4j
@RestController
public class AdminCategoryApiController {

    @GetMapping
    public String getCategoryList(){
        return "ok";
    }

    @PostMapping
    public String registerCategory(){
        return "ok";
    }

    @PostMapping("/{categoryName}")
    public String editCategory(@PathVariable Long categoryName){
        return "ok";
    }

    @DeleteMapping("/{categoryName}")
    public String deleteCategory(@PathVariable Long categoryName){
        return "ok";
    }

}
