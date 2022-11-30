package com.bapp.donationserver.controller.api.user;

import com.bapp.donationserver.data.Campaign;
import com.bapp.donationserver.data.Member;
import com.bapp.donationserver.data.dto.MemberDto;
import com.bapp.donationserver.data.consts.SessionConst;
import com.bapp.donationserver.data.status.Return;
import com.bapp.donationserver.service.account.AccountService;
import com.bapp.donationserver.service.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequestMapping("/api/user")
@Slf4j
@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final AccountService accountService;
    private final TransactionService transactionService;

    /**
     * 클라이언트 정보 : 사용자 id
     * 내 정보, 후원 이력 조회
     * <p>
     * 서버 응답 : 처리 결과 응답
     */
    @GetMapping
    public Return getMyPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto) {
        Member member = accountService.getMember(memberDto.getEmail());
        return Return.successStatusWithData(new MemberDto(member));
    }

    @PostMapping
    public Return editMyPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto member,
                             @RequestBody MemberDto data) {
        MemberDto.checkValidation(data);
        accountService.updateMember(member.getEmail(), data);
        return Return.successStatus();
    }

    @GetMapping("/tx")
    public Return getTransactions(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto member) {
        return Return.successStatusWithData(
                transactionService.getSimpleHistories(member.getWalletId()));
    }

    /**
     * 회원 탈퇴
     */
    @DeleteMapping
    public Return quitService(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        accountService.deleteMember(member);

        return Return.successStatus();
    }

    /**
     * 로그아웃 요청
     */
    @GetMapping("/logout")
    public Return logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null)
            return Return.failStatus("API 요청이 처리되지 않았습니다.");

        session.invalidate();
        return Return.successStatus();
    }

    /**
     * 클라이언트 : 사용자 id -> email로 수정
     * 응답 정보 : 표지이미지, 켐페인 제목, 재단 이름, 마감일, 현재 모금 금액, 목표 금액
     */
    @GetMapping("/info")
    public Return myDonationList(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto member) {
        return Return.successStatusWithData(
                accountService.getDonationListByEmail(member.getEmail()));
    }


    /**
     * 클라이언트 : 사용자 id, 포인트 충전 금액
     * 서버 응답 : success fail
     */
    @PostMapping("/pay")
    public Return pay(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto member,
                      @RequestParam Long amount) {
        if (amount < 0) {
            return Return.failStatus("결제 가능 금액은 100원 이상입니다.");
        }

        transactionService.pay(member.getEmail(), amount);
        return Return.successStatus();
    }

    @GetMapping("/payback")
    public Return payBack(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto member,
                          @RequestParam Long amount) {
        if (member.getPointAmount() < amount) {
            return Return.failStatus("요청한 금액보다 포인트가 적습니다.");
        }
        if (amount < 100) {
            return Return.failStatus("환불 요청 가능한 금액은 100원 이상입니다.");
        }

        transactionService.payback(member.getEmail(), amount);

        return Return.successStatus();
    }

    @GetMapping("/give")
    public Object donate(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto member,
                         @SessionAttribute(name = SessionConst.LAST_CHECK_CAMPAIGN, required = false) Campaign campaign,//최근 조회한 켐페인 등록
                         @RequestParam Long amount) {

        if (campaign == null) {
            return Return.failStatus("최근 조회한 켐페인이 없습니다.");
        }
        if (member.getPointAmount() < amount) {
            return Return.failStatus("요청한 금액보다 포인트가 적습니다.");
        }
        if (amount <= 100) {
            return Return.failStatus("기부 최소 금액은 100원 입니다.");
        }

        transactionService.donate(member.getEmail(), campaign, amount);//기부 성공시 DonatedCampaign 도메인 데이터 추가
        accountService.addDonatedCampaign(member.getEmail(), campaign);

        return Return.successStatus();
    }
}