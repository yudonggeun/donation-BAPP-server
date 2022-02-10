package com.bapp.donationserver.user.controller;

import com.bapp.donationserver.data.dto.MemberDto;
import com.bapp.donationserver.user.service.MemberService;
import com.bapp.donationserver.user.service.NormalUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    public MemberDto getMyPage(HttpServletRequest request, @RequestParam String email) {
        log.info("내 정보 조회 : email={}", email);
        return memberService.getMemberInformation(email);
    }

    @PostMapping
    public String editMyPage(@RequestBody MemberDto data) {
        log.info("내 정보 수정 : 전달된 데이터 {}", data);
        memberService.updateMemberInformation(data.getEmail(), data);
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
    public String login(@RequestParam String email,
                        @RequestParam String password) {
        MemberDto member = memberService.getMemberInformation(email);
        return member.getPassword().equals(password) ? "success" : "fail";
    }

    /**
     * 클라이언트 : 사용자 id, 포인트 충전 금액
     * 서버 응답 : success fail
     */
    @PostMapping("/pay")
    public String payComplete(@RequestParam String email, @RequestParam Long amount) {
        log.info("email={}, amount={}", email, amount);
        normalUserService.pay(email, amount);
        return "success";
    }
}
