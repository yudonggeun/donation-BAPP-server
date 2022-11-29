package com.bapp.donationserver.controller.api.newUser;

import com.bapp.donationserver.data.Member;
import com.bapp.donationserver.data.consts.SessionConst;
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
public class NewAccountApiController {

    private final AccountService memberService;
    /**
     * 클라이언트 전송 : 이메일, 패스워드
     * 서버 응답 : 세션 아이디, fail
     */
    @PostMapping("/login")
    public Object login(@RequestBody LoginDto loginForm, HttpServletRequest request) throws Exception {

        //로직 실행
        Member member = memberService.login(loginForm.getEmail(), loginForm.getPassword());

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, new MemberDto(member));
        return Status.successStatus();
    }

    /**
     * 클라이언트 전달 정보 : 회원 유형
     * 일반 사용자 : 이름, 번호, 이메일, 패스워드, 닉네임, 프로필 사진
     * 단체 사용자 : 단체이름, 번호, 이메일, 패스워드, 닉네임, 프로필 사진 -> 승인 후 가입
     */
    @PostMapping
    public Object newUser(@RequestBody MemberDto data) {

        // [타당성 검증] : 올바른 형식 확인, null 확인, 관리자 계정 생성 방지
        MemberDto.checkValidation(data);
        MemberDto.checkNotNull(data);

        if(data.getMemberType() == MemberType.ADMIN){
            log.info("관리자 권한을 가진 계정 생성을 요청할 수 없습니다.");
            return Status.failStatus("API 요청이 처리되지 않았습니다.");
        }

        //로직 실행
        memberService.createMember(data);
        return Status.successStatus();
    }

}
