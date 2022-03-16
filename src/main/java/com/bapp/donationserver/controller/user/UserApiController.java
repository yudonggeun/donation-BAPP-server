package com.bapp.donationserver.controller.user;

import com.bapp.donationserver.data.Member;
import com.bapp.donationserver.data.dto.CampaignSimpleDto;
import com.bapp.donationserver.data.dto.MemberDto;
import com.bapp.donationserver.data.consts.SessionConst;
import com.bapp.donationserver.data.status.Status;
import com.bapp.donationserver.service.account.AccountService;
import com.bapp.donationserver.service.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

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
     *
     * 서버 응답 : 처리 결과 응답
     */
    @GetMapping
    public MemberDto getMyPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                               HttpServletRequest request) {
        //log
        log.info("내 정보 조회 : email={}", member.getEmail());

        return member.getDto();
    }

    @PostMapping
    public Object editMyPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                             @RequestBody MemberDto data) {

        log.info("내 정보 수정 : 전달된 데이터 {}", data);

        accountService.updateMember(member, data);
        return Status.successStatus();
    }

    /**
     * 회원 탈퇴
     */
    @DeleteMapping
    public Object quitService(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        accountService.dropMember(member);

        return Status.successStatus();
    }

    /**
     * 로그아웃 요청
     */
    @GetMapping("/logout")
    public Object logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session == null)
            return Status.failStatus("API 요청이 처리되지 않았습니다.");
        session.invalidate();
        return Status.successStatus();
    }

    /**
     * 클라이언트 : 사용자 id -> email로 수정
     * 응답 정보 : 표지이미지, 켐페인 제목, 재단 이름, 마감일, 현재 모금 금액, 목표 금액
     */
    @GetMapping("/info")
    public List<CampaignSimpleDto> myDonationList(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {

        return accountService.checkMyDonationList(member);
    }


    /**
     * 클라이언트 : 사용자 id, 포인트 충전 금액
     * 서버 응답 : success fail
     */
    @PostMapping("/pay")
    public Object payComplete(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                              @RequestParam Long amount) {

        log.info("email={}, amount={}", member.getEmail(), amount);
        transactionService.pay(member, amount);
        return Status.successStatus();
    }
}