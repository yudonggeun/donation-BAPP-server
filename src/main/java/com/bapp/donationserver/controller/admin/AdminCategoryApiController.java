package com.bapp.donationserver.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/admin/category")
@Slf4j
@RestController
public class AdminCategoryApiController {

    @GetMapping
    public String getCategoryList(){
        return null;
    }

    @PostMapping
    public String registerCategory(){
        return null;
    }

    @PostMapping("/{categoryName}")
    public String editCategory(@PathVariable Long categoryName){
        return null;
    }

    @DeleteMapping("/{categoryName}")
    public String deleteCategory(@PathVariable Long categoryName){
        return null;
    }

}
