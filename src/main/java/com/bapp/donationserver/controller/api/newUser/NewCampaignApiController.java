package com.bapp.donationserver.controller.api.newUser;

import com.bapp.donationserver.entity.Campaign;
import com.bapp.donationserver.data.consts.SessionConst;
import com.bapp.donationserver.data.dto.*;
import com.bapp.donationserver.data.status.Return;
import com.bapp.donationserver.data.type.MemberType;
import com.bapp.donationserver.service.campaign.CampaignService;
import com.bapp.donationserver.service.category.CategoryService;
import com.bapp.donationserver.service.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/new/campaign")
@Slf4j
@RestController
@RequiredArgsConstructor
public class NewCampaignApiController {

    private final CampaignService campaignService;
    private final TransactionService transactionService;
    private final CategoryService categoryService;

    /**
     * 검색 조건 : 페이지 정보, 단채명, 제목, 카테고리 중복, 관심
     */
    @PostMapping
    public Return userSearchCampaign(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto,
                                                                @RequestBody CampaignSearchConditionDto searchCondition) {
        MemberType type = (memberDto != null ? memberDto.getMemberType() : MemberType.USER);// 회원이 아니 라면 일반 유저 권한 부여 검색
        List<CampaignSimpleDto> dtoList = campaignService.getCampaignList(searchCondition, type);
        return Return.successStatusWithData(dtoList);
    }

    /**
     * 서버 전송 : 표지이미지, 상세이미지, 캠페인 제목, 재단 이름, 마감일, 현재 모금 금액, 목표 금액, 카테고리, 계획
     */
    @GetMapping("/{campaignId}")
    public Return getCampaignDetail(@PathVariable Long campaignId) {
        Campaign campaign = campaignService.getCampaign(campaignId);
        return Return.successStatusWithData(new CampaignFullDto(campaign));
    }

    /**
     * 기부 내역(거래 내역) : 사용처, 출금(환전)액, 거래 내용, 출금 시간
     * 기부 내역 배열 전송
     * 존재하지 않은 켐페인 id로 조회시 빈 배열을 반환한다.
     */
    @GetMapping("/history")
    public Return getCampaignHistory(@RequestParam Long campaignId) {
        return Return.successStatusWithData(transactionService.getHistories(campaignId));
    }

    @GetMapping("/category")
    public Return getCategoryList(){
        return Return.successStatusWithData(categoryService.getCategoryList());
    }
}
