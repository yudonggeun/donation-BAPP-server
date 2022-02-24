package com.bapp.donationserver.controller.admin;

import com.bapp.donationserver.data.status.Status;
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
    public Object registerCategory(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto,
                                   @RequestBody CategoryDto categoryDto){

        if(memberDto == null || memberDto.getMemberType() != MemberType.ADMIN){
            return Status.failStatus("API 요청이 처리되지 않았습니다.");
        }

        adminService.registerCategory(categoryDto);
        return Status.successStatus();
    }

    @PostMapping("/{categoryName}")
    public Object editCategory(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto,
                               @PathVariable String categoryName, @RequestBody CategoryDto categoryDto){

        if(memberDto == null || memberDto.getMemberType() != MemberType.ADMIN){
            return Status.failStatus("API 요청이 처리되지 않았습니다.");
        }

        adminService.modifyCategory(categoryName, categoryDto);
        return Status.successStatus();
    }

    @DeleteMapping("/{categoryName}")
    public Object deleteCategory(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto,
                                 @PathVariable String categoryName){

        if(memberDto == null || memberDto.getMemberType() != MemberType.ADMIN){
            return Status.failStatus("API 요청이 처리되지 않았습니다.");
        }

        adminService.deleteCategory(categoryName);
        return Status.successStatus();
    }

}
