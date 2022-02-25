package com.bapp.donationserver.controller.user;

import com.bapp.donationserver.data.dto.CampaignSimpleDto;
import com.bapp.donationserver.data.type.MemberType;
import com.bapp.donationserver.data.SessionConst;
import com.bapp.donationserver.data.dto.LoginDto;
import com.bapp.donationserver.data.dto.MemberDto;
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

    private final AccountService memberService;
    private final TransactionService normalUserService;

    /**
     * 클라이언트 정보 : 사용자 id
     * 내 정보, 후원 이력 조회
     *
     * 서버 응답 : 처리 결과 응답
     */
    @GetMapping
    public MemberDto getMyPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto,
                               HttpServletRequest request) {
        //log
        log.info("내 정보 조회 : email={}", memberDto.getEmail());

        return memberService.getMemberInformation(memberDto.getEmail());
    }

    @PostMapping
    public Object editMyPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto,
                             @RequestBody MemberDto data) {

        log.info("내 정보 수정 : 전달된 데이터 {}", data);

        memberService.updateMemberInformation(memberDto.getEmail(), data);
        return Status.successStatus();
    }

    /**
     * 회원 탈퇴
     */
    @DeleteMapping
    public Object quitService(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        MemberDto memberDto = (MemberDto) session.getAttribute(SessionConst.LOGIN_MEMBER);
        memberService.dropMember(memberDto.getEmail());

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
    public List<CampaignSimpleDto> myDonationList(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto) {

        return memberService.checkMyDonationList(memberDto.getEmail());
    }


    /**
     * 클라이언트 : 사용자 id, 포인트 충전 금액
     * 서버 응답 : success fail
     */
    @PostMapping("/pay")
    public Object payComplete(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto,
                              @RequestParam Long amount) {

        log.info("email={}, amount={}", memberDto.getEmail(), amount);
        normalUserService.pay(memberDto.getWalletId(), amount);
        return Status.successStatus();
    }
}