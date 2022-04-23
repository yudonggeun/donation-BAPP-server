package com.bapp.donationserver.exception;

import com.bapp.donationserver.data.dto.CampaignFullDto;
import com.bapp.donationserver.data.type.MemberType;
import com.bapp.donationserver.repository.WalletRepository;
import com.bapp.donationserver.service.category.CategoryService;
import com.bapp.donationserver.data.*;
import com.bapp.donationserver.data.dto.MemberDto;
import com.bapp.donationserver.service.account.AccountService;
import com.bapp.donationserver.service.campaign.CampaignService;
import com.bapp.donationserver.service.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitData {
    private final AccountService memberService;
    private final CampaignService campaignService;
    private final CategoryService adminService;

    @PostConstruct
    public void init(){
//        initMember();
//        initCategory("아동");
//        initCategory("청년");
//        initCampaign();
    }

    public void initMember(){
        MemberDto myPageDto = new MemberDto();
        myPageDto.setEmail("ydong98@gmail.com");
        myPageDto.setName("유동근");
        myPageDto.setPassword("qwerasdf2@");
        myPageDto.setNickname("test_admin");
        myPageDto.setPhoneNumber("010-0000-0000");
        myPageDto.setMemberType(MemberType.ADMIN);
        myPageDto.setProfilePhotoName("profile-path");

        memberService.newMember(myPageDto);
    }

    public void initCampaign(){

        addSampleCampaign("테스트 켐페인1");
        addSampleCampaign("테스트 켐페인2");
    }

    private void addSampleCampaign(String subject) {
        CampaignFullDto campaignInfo = new CampaignFullDto();

        campaignInfo.setCampaignName(subject);
        campaignInfo.setCharityName("몬스터주식회사");
        campaignInfo.setDeadline(LocalDate.now());
        campaignInfo.setGoalAmount(1000000L);
        ArrayList<String> test = new ArrayList<>();
        test.add("아동");
        test.add("청년");
        campaignInfo.setCategories(test);
        campaignInfo.setCoverImagePath("/path/child");
        campaignInfo.setDetailImagePath("/path/detail/child");
        log.info("켐패인 추가 ={}", campaignInfo);
        campaignService.registerCampaign(campaignInfo);
    }

    private void initCategory(String name){
        Category category = new Category();
        category.setName(name);
        adminService.registerCategory(category.getDto());
    }

}
