package com.bapp.donationserver.controller.api.admin;

import com.bapp.donationserver.data.Member;
import com.bapp.donationserver.data.status.Status;
import com.bapp.donationserver.service.category.CategoryService;
import com.bapp.donationserver.data.dto.CategoryDto;
import com.bapp.donationserver.data.type.MemberType;
import com.bapp.donationserver.data.consts.SessionConst;
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
    public Object registerCategory(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                                   @RequestBody CategoryDto categoryDto){

        if(member.getMemberType() != MemberType.ADMIN){
            return Status.failStatus("API 요청이 처리되지 않았습니다.");
        }

        categoryService.registerCategory(categoryDto);
        return Status.successStatus();
    }

    @PostMapping("/{categoryName}")
    public Object editCategory(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                               @PathVariable String categoryName, @RequestBody CategoryDto categoryDto){

        if(member.getMemberType() != MemberType.ADMIN){
            return Status.failStatus("API 요청이 처리되지 않았습니다.");
        }

        categoryService.modifyCategory(categoryName, categoryDto);
        return Status.successStatus();
    }

    @DeleteMapping("/{categoryName}")
    public Object deleteCategory(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                                 @PathVariable String categoryName){

        if(member.getMemberType() != MemberType.ADMIN){
            return Status.failStatus("API 요청이 처리되지 않았습니다.");
        }

        categoryService.deleteCategory(categoryName);
        return Status.successStatus();
    }

}
