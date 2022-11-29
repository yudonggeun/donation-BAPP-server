package com.bapp.donationserver.controller.newUser;

import com.bapp.donationserver.data.Campaign;
import com.bapp.donationserver.data.Member;
import com.bapp.donationserver.data.consts.SessionConst;
import com.bapp.donationserver.data.dto.CampaignFullDto;
import com.bapp.donationserver.data.dto.CampaignSearchConditionDto;
import com.bapp.donationserver.data.dto.CampaignSimpleDto;
import com.bapp.donationserver.data.dto.TransactionDetailDto;
import com.bapp.donationserver.data.type.MemberType;
import com.bapp.donationserver.service.campaign.CampaignService;
import com.bapp.donationserver.service.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/new/campaign")
@Slf4j
@Controller
@RequiredArgsConstructor
public class NewCampaignController {

    private final CampaignService campaignService;
    private final TransactionService transactionService;

    @GetMapping
    public String campaignListPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                                   Model model) {
        MemberType type = (member != null ? member.getMemberType() : MemberType.USER); // 회원이 아니라면 일반 유저 권한 부여 검색

        List<CampaignSimpleDto> dtoList = campaignService.checkCampaignList(new CampaignSearchConditionDto(), type);
        model.addAttribute("campaignList", dtoList);
        return "/campaign/campaignList.html";
    }

    /**
     * 검색 조건 : 페이지 정보, 단채명, 제목, 카테고리 중복, 관심
     */
    @PostMapping
    public String userSearchCampaign(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                                     @ModelAttribute CampaignSearchConditionDto searchCondition,
                                     Model model) {

        MemberType type = (member != null ? member.getMemberType() : MemberType.USER); // 회원이 아니라면 일반 유저 권한 부여 검색

        List<CampaignSimpleDto> dtoList = campaignService.checkCampaignList(searchCondition, type);
        model.addAttribute("campaignList", dtoList);
        return "/campaign/campaignList.html";
    }

    /**
     * 서버 전송 : 표지이미지, 상세이미지, 캠페인 제목, 재단 이름, 마감일, 현재 모금 금액, 목표 금액, 카테고리, 계획
     */
    @GetMapping("/{campaignId}")
    public String getCampaignDetail(@PathVariable Long campaignId, HttpServletRequest request, Model model) {
        Campaign campaign = campaignService.getDetailsOfCampaign(campaignId);

        request.getSession().setAttribute(SessionConst.LAST_CHECK_CAMPAIGN, campaign);
        model.addAttribute("campaign", new CampaignFullDto(campaign));
        return "/campaign/detailCampaign.html";
    }


    /**
     * 기부 내역(거래 내역) : 사용처, 출금(환전)액, 거래 내용, 출금 시간
     * 기부 내역 배열 전송
     * 존재하지 않은 켐페인 id로 조회시 빈 배열을 반환한다.
     */
    @GetMapping("/history")
    public List<TransactionDetailDto> getCampaignHistory(@RequestParam Long campaignId) {
        return transactionService.getTransactionHistory(campaignId);
    }
}
