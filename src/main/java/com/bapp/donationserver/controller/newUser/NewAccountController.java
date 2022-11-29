package com.bapp.donationserver.controller.newUser;

import com.bapp.donationserver.data.Member;
import com.bapp.donationserver.data.consts.SessionConst;
import com.bapp.donationserver.data.dto.LoginDto;
import com.bapp.donationserver.data.dto.MemberDto;
import com.bapp.donationserver.data.status.Status;
import com.bapp.donationserver.data.type.MemberType;
import com.bapp.donationserver.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RequestMapping
@Slf4j
@Controller
@RequiredArgsConstructor
public class NewAccountController {

    private final AccountService memberService;

    @RequestMapping
    public String indexPage() {
        return "index.html";
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.getSession().invalidate();

        response.sendRedirect("/");
    }

    @GetMapping("/login")
    public String loginFormPage() {
        return "login.html";
    }

    /**
     * 클라이언트 전송 : 이메일, 패스워드
     * 서버 응답 : 세션 아이디, fail
     */
    @PostMapping("/new/login")
    public void login(@ModelAttribute LoginDto loginForm, HttpServletRequest request, HttpServletResponse response) throws Exception {

        //로직 실행
        Member member = memberService.login(loginForm.getEmail(), loginForm.getPassword());

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);

        response.sendRedirect("/");
    }

    /**
     * 클라이언트 전달 정보 : 회원 유형
     * 일반 사용자 : 이름, 번호, 이메일, 패스워드, 닉네임, 프로필 사진
     * 단체 사용자 : 단체이름, 번호, 이메일, 패스워드, 닉네임, 프로필 사진 -> 승인 후 가입
     */
    @PostMapping("/new")
    public Object newUser(@RequestBody MemberDto data) {

        // [타당성 검증] : 올바른 형식 확인, null 확인, 관리자 계정 생성 방지
        MemberDto.checkValidation(data);
        MemberDto.checkNotNull(data);

        if (data.getMemberType() == MemberType.ADMIN) {
            log.info("관리자 권한을 가진 계정 생성을 요청할 수 없습니다.");
            return Status.failStatus("API 요청이 처리되지 않았습니다.");
        }

        //로직 실행
        return memberService.createMember(data) ? Status.successStatus() : Status.failStatus("회원 생성 실패");
    }

}
