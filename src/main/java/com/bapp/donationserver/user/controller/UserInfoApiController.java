package com.bapp.donationserver.user.controller;

import com.bapp.donationserver.data.CampaignInfo;
import com.bapp.donationserver.data.MemberInfo;
import com.bapp.donationserver.data.Transaction;
import com.bapp.donationserver.data.dto.CampaignInfoSampleDto;
import com.bapp.donationserver.data.dto.TransactionDto;
import com.bapp.donationserver.user.service.MemberService;
import com.bapp.donationserver.user.service.NormalUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/user/info")
@Slf4j
@RestController
@RequiredArgsConstructor
public class UserInfoApiController {

    private final NormalUserService normalUserService;
    private final MemberService memberService;

   /* @GetMapping
    public String checkDonationHistory(){
        return "ok";
    }*/

    /**
     * 클라이언트 : 사용자 id -> email로 수정
     * 응답 정보 : 표지이미지, 켐페인 제목, 재단 이름, 마감일, 현재 모금 금액, 목표 금액
     */
    @GetMapping("/{email}")
    public List<CampaignInfoSampleDto> myDonationList(@PathVariable String email){

        List<CampaignInfoSampleDto> donationList = new ArrayList<>();
        memberService.getMemberInformation(email).getDonatedCampaigns()
                .forEach(campaignInfo -> {
                    CampaignInfoSampleDto dto = new CampaignInfoSampleDto();
                    dto.setCampaignInfo(campaignInfo);
                    donationList.add(dto);
                });

        return donationList;
    }

    /**
     * 기부 내역(거래 내역) : 사용처, 출금(환전)액, 거래 내용, 출금 시간
     * 기부 내역 배열 전송
     */
    @GetMapping
    public List<TransactionDto> checkCampaignHistory(@RequestParam String campaignId){

        List<TransactionDto> list = new ArrayList<>();
        normalUserService.checkDonationHistory(campaignId).forEach(transaction -> list.add(transaction.getDto()));

        return list;
    }
}
