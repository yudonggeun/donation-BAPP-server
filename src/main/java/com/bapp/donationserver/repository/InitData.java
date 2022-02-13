package com.bapp.donationserver.repository;

import com.bapp.donationserver.admin.service.AdminService;
import com.bapp.donationserver.charity.service.CharityService;
import com.bapp.donationserver.data.*;
import com.bapp.donationserver.data.dto.MemberDto;
import com.bapp.donationserver.user.service.MemberService;
import com.bapp.donationserver.user.service.NormalUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InitData {
    private final MemberService memberService;
    private final NormalUserService normalUserService;
    private final CharityService charityService;
    private final AdminService adminService;

    @PostConstruct
    public void init(){
        testTransaction();
        initMember();
        initCampaign();
        initCategory("아동");
        initCategory("청년");
    }
    public void testTransaction(){
        Transaction transaction = new Transaction(
                new Campaign(),
                "me",
                "you",
                10000L,
                20000L
        );
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

        normalUserService.joinMember(myPageDto);
    }

    public void initCampaign(){

        addSampleCampaign("아동");
        addSampleCampaign("청년");
    }

    private void addSampleCampaign(String subject) {
        Campaign campaignInfo = new Campaign(UUID.randomUUID().toString());

        campaignInfo.setCampaignName(subject);
        campaignInfo.setCharityName("몬스터주식회사");
        campaignInfo.setDeadline(LocalDate.now());
        campaignInfo.setCurrentAmount(1000L);
        campaignInfo.setGoalAmount(1000000L);

        campaignInfo.setCoverImagePath("/path/child");
        campaignInfo.setDetailImagePath("/path/detail/child");

        charityService.registerCampaign(campaignInfo.getCampaignFullDto());
    }

    private void initCategory(String name){
        Category category = new Category();
        category.setName(name);
        adminService.registerCategory(category.getDto());
    }

}
