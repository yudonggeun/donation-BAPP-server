package com.bapp.donationserver.controller.admin;

import com.bapp.donationserver.service.admin.AdminServiceImpl;
import com.bapp.donationserver.data.dto.CategoryDto;
import com.bapp.donationserver.data.MemberType;
import com.bapp.donationserver.data.SessionConst;
import com.bapp.donationserver.data.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/admin/category")
@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminCategoryApiController {

    private final AdminServiceImpl adminService;

    @GetMapping
    public List<CategoryDto> getCategoryList(){
        return adminService.getCategoryList();
    }

    @PostMapping
    public String registerCategory(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto,
                                   @RequestBody CategoryDto categoryDto){

        if(memberDto == null || memberDto.getMemberType() != MemberType.ADMIN){
            return "fail";
        }

        adminService.registerCategory(categoryDto);
        return "success";
    }

    @PostMapping("/{categoryName}")
    public String editCategory(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto,
                               @PathVariable String categoryName, @RequestBody CategoryDto categoryDto){

        if(memberDto == null || memberDto.getMemberType() != MemberType.ADMIN){
            return "fail";
        }

        adminService.modifyCategory(categoryName, categoryDto);
        return "success";
    }

    @DeleteMapping("/{categoryName}")
    public String deleteCategory(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto,
                                 @PathVariable String categoryName){

        if(memberDto == null || memberDto.getMemberType() != MemberType.ADMIN){
            return "fail";
        }

        adminService.deleteCategory(categoryName);
        return "ok";
    }

}
