package com.bapp.donationserver.controller.admin;

import com.bapp.donationserver.data.Member;
import com.bapp.donationserver.data.consts.SessionConst;
import com.bapp.donationserver.data.dto.CategoryDto;
import com.bapp.donationserver.data.status.Status;
import com.bapp.donationserver.data.type.MemberType;
import com.bapp.donationserver.service.campaign.CampaignService;
import com.bapp.donationserver.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;

@RequestMapping("/admin")
@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminController {

    private final CategoryService adminService;
    private final CampaignService campaignService;

    @GetMapping
    public String AdminPage(){
        return "admin/admin_home.html";
    }

    @GetMapping("/limit/{campaignId}")
    public RedirectView changeLimitCampaign(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                                      @PathVariable Long campaignId,
                                      @RequestParam Boolean isAccept, Model model){
        Map<String, Object> stringObjectMap = model.asMap();
        Set<String> strings = stringObjectMap.keySet();
        for (String string : strings) {
            Object value = stringObjectMap.get(string);
            log.info("name = {}, value={}", string, value);
        }

        campaignService.acceptCampaign(campaignId, isAccept);
        return new RedirectView("/new/campaign");
    }

    @GetMapping("/category")
    public String getCategoryList(Model model){

        model.addAttribute("categories", adminService.getCategoryList());
        model.addAttribute("category", new CategoryDto(""));
        return "admin/category/category_home.html";
    }

    @GetMapping("/category/add")
    public String addCategory(Model model) {

        model.addAttribute("category", new CategoryDto(""));
        return "admin/category/addCategory.html";
    }


    @PostMapping("/category")
    public RedirectView registerCategory(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                                   @ModelAttribute CategoryDto categoryDto){

        if(member.getMemberType() != MemberType.ADMIN){
            throw new IllegalStateException("error");
        }

        adminService.registerCategory(categoryDto);
        return new RedirectView("/admin/category");
    }

    @GetMapping("/category/{categoryName}")
    public String editCategoryPage(@PathVariable String categoryName, Model model){
        model.addAttribute("category", new CategoryDto(categoryName));
        return "admin/category/modifyCategory.html";
    }

    @PostMapping("/category/{categoryName}")
    public RedirectView editCategory(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                               @PathVariable String categoryName, @ModelAttribute("category") CategoryDto categoryDto){

        if(member.getMemberType() != MemberType.ADMIN){
            throw new IllegalStateException("error");
        }

        adminService.modifyCategory(categoryName, categoryDto);
        return new RedirectView("/admin/category");
    }

    @PostMapping("/category/delete/{categoryName}")
    public RedirectView deleteCategory(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                                 @PathVariable String categoryName){

        if(member.getMemberType() != MemberType.ADMIN){
            throw new IllegalStateException("error");
        }

        adminService.deleteCategory(categoryName);
        return new RedirectView("/admin/category");
    }

}
