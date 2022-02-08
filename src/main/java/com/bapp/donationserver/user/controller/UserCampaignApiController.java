package com.bapp.donationserver.user.controller;

import com.bapp.donationserver.data.CampaignInfo;
import com.bapp.donationserver.data.CampaignSearchCondition;
import com.bapp.donationserver.data.url.CampaignInfoSampleDto;
import com.bapp.donationserver.user.service.MemberService;
import com.bapp.donationserver.user.service.NormalUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RequestMapping("/api/user/campaign")
@Slf4j
@RestController
@RequiredArgsConstructor
public class UserCampaignApiController {

    private final MemberService memberService;
    private final NormalUserService normalUserService;


    /**
     * 캠패인 정보 : 표지이미지, 켐페인 제목, 재단 이름, 마감일, 현재 모금 금액, 목표 금액
     */
    @GetMapping
    public List<CampaignInfoSampleDto> getCampaignList() {

        CampaignSearchCondition searchCondition = new CampaignSearchCondition();
        searchCondition.setStartIndex(0);
        searchCondition.setEndIndex(4);

        return searchCampaign(searchCondition);
    }

    /**
     * 검색 조건 : 페이지 정보, 단채명, 제목, 카테고리 중복, 관심
     */
    @PostMapping
    public List<CampaignInfoSampleDto> searchCampaign(@RequestBody CampaignSearchCondition searchCondition) {

        List<CampaignInfoSampleDto> dtoList = new ArrayList<>();
        List<CampaignInfo> campaignInfos = normalUserService.checkCampaignList(searchCondition);

        if(campaignInfos == null) {
            log.info("검색 조건에 부합하는 캠페인이 없습니다.");
            return dtoList;
        }

        for (CampaignInfo campaignInfo : campaignInfos) {
            dtoList.add(new CampaignInfoSampleDto().setCampaignInfo(campaignInfo));
        }

        log.info("정상 반환 ={}", dtoList);
        return dtoList;
    }

    /**
     * 서버 전송 : 표지이미지, 상세이미지, 캠페인 제목, 재단 이름, 마감일, 현재 모금 금액, 목표 금액, 카테고리, 계획
     */
    @GetMapping("/{campaignId}")
    public CampaignInfo getCampaignDetail(@PathVariable String campaignId) {
        CampaignInfo campaignInfo = normalUserService.checkDetailsOfCampaign(campaignId);
        if(campaignInfo == null){
            throw new IllegalStateException(campaignId + " 켐패인이 존재하지 않습니다.");
        }
        return campaignInfo;
    }

}
