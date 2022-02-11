package com.bapp.donationserver.user.controller;

import com.bapp.donationserver.data.Member;
import com.bapp.donationserver.data.SessionConst;
import com.bapp.donationserver.data.dto.LoginDto;
import com.bapp.donationserver.data.dto.MemberDto;
import com.bapp.donationserver.user.service.MemberService;
import com.bapp.donationserver.user.service.NormalUserService;
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

    private final MemberService memberService;
    private final NormalUserService normalUserService;

    /**
     * 클라이언트 정보 : 사용자 id
     * 내 정보, 후원 이력 조회
     * <p>
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
    public String editMyPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto,
                             @RequestBody MemberDto data) {

        log.info("내 정보 수정 : 전달된 데이터 {}", data);

        memberService.updateMemberInformation(memberDto.getEmail(), data);
        return "ok";
    }

    /**
     * 클라이언트 전달 정보 : 회원 유형
     * 일반 사용자 : 이름, 번호, 이메일, 패스워드, 닉네임, 프로필 사진
     * 단체 사용자 : 단체이름, 번호, 이메일, 패스워드, 닉네임, 프로필 사진 -> 승인 후 가입
     */
    @PutMapping
    public String newUser(@RequestBody MemberDto data) {

        log.info("회원가입 : 전달된 데이터 {}", data);

        normalUserService.joinMember(data);
        return "ok";
    }

    /**
     * 클라이언트 전송 : 이메일, 패스워드
     * 서버 응답 : 세션 아이디, fail
     */
    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginForm, HttpServletRequest request) {

        MemberDto member = normalUserService.login(loginForm.getEmail(), loginForm.getPassword());

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);
        return "success";
    }

    /**
     * 클라이언트 : 사용자 id, 포인트 충전 금액
     * 서버 응답 : success fail
     */
    @PostMapping("/pay")
    public String payComplete(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto,
                              @RequestParam Long amount) {

        log.info("email={}, amount={}", memberDto.getEmail(), amount);
        normalUserService.pay(memberDto.getEmail(), amount);
        return "success";
    }
}
