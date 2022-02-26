package com.bapp.donationserver.exception;

import com.bapp.donationserver.data.dto.CampaignFullDto;
import com.bapp.donationserver.data.type.MemberType;
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
        initMember();
        initCampaign();
        initCategory("아동");
        initCategory("청년");
    }

    public void initMember(){
        MemberDto myPageDto = new MemberDto();
        myPageDto.setEmail("ydong98@gmail.com");
        myPageDto.setName("유동근");
        myPageDto.setPassword("psasdflskdj");
        myPageDto.setNickname("test1234");
        myPageDto.setPhoneNumber("010-1111-1111");
        myPageDto.setMemberType(MemberType.ADMIN);
        myPageDto.setProfilePhotoName("picture");

        memberService.newMember(myPageDto);
    }

    public void initCampaign(){

        addSampleCampaign("아동");
        addSampleCampaign("청년");
    }

    private void addSampleCampaign(String subject) {
        CampaignFullDto campaignInfo = new CampaignFullDto();

        campaignInfo.setCampaignName(subject);
        campaignInfo.setCharityName("몬스터주식회사");
        campaignInfo.setDeadline(LocalDate.now());
        campaignInfo.setGoalAmount(1000000L);

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
