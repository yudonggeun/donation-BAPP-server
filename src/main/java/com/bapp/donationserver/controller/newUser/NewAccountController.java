package com.bapp.donationserver.controller.newUser;

import com.bapp.donationserver.data.SessionConst;
import com.bapp.donationserver.data.dto.LoginDto;
import com.bapp.donationserver.data.dto.MemberDto;
import com.bapp.donationserver.data.status.Status;
import com.bapp.donationserver.data.type.MemberType;
import com.bapp.donationserver.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequestMapping("/api/new")
@Slf4j
@RestController
@RequiredArgsConstructor
public class NewAccountController {

    private final AccountService memberService;
    /**
     * 클라이언트 전송 : 이메일, 패스워드
     * 서버 응답 : 세션 아이디, fail
     */
    @GetMapping("/login")
    public Object login(@ModelAttribute LoginDto loginForm, HttpServletRequest request) {

        MemberDto member = memberService.login(loginForm.getEmail(), loginForm.getPassword());

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);
        return Status.successStatus();
    }

    /**
     * 클라이언트 전달 정보 : 회원 유형
     * 일반 사용자 : 이름, 번호, 이메일, 패스워드, 닉네임, 프로필 사진
     * 단체 사용자 : 단체이름, 번호, 이메일, 패스워드, 닉네임, 프로필 사진 -> 승인 후 가입
     */
    @PostMapping
    public Object newUser(@RequestBody MemberDto data) {

        log.info("회원가입 : 전달된 데이터 {}", data);
        if(data.getMemberType() == MemberType.ADMIN){
            log.info("관리자 권한을 가진 계정 생성을 요청할 수 없습니다.");
            return Status.failStatus("API 요청이 처리되지 않았습니다.");
        }
        memberService.newMember(data);
        return Status.successStatus();
    }
}
