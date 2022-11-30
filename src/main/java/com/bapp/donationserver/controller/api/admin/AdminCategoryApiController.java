package com.bapp.donationserver.controller.api.admin;

import com.bapp.donationserver.data.status.Return;
import com.bapp.donationserver.service.category.CategoryService;
import com.bapp.donationserver.data.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/admin/category")
@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminCategoryApiController {

    private final CategoryService categoryService;

    @PostMapping
    public Return registerCategory(@RequestBody CategoryDto categoryDto) {
        categoryService.registerCategory(categoryDto);
        return Return.successStatus();
    }

    @PostMapping("/{categoryName}")
    public Return editCategory(@PathVariable String categoryName,
                               @RequestBody CategoryDto categoryDto) {
        categoryService.modifyCategory(categoryName, categoryDto);
        return Return.successStatus();
    }

    @DeleteMapping("/{categoryName}")
    public Return deleteCategory(@PathVariable String categoryName) {
        categoryService.deleteCategory(categoryName);
        return Return.successStatus();
    }

}
