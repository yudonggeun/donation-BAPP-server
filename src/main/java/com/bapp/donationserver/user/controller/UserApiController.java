package com.bapp.donationserver.user.controller;

import com.bapp.donationserver.user.NewUserForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/user")
@Slf4j
@RestController
public class UserApiController {
    /**
     * 클라이언트 정보 : 사용자 id
     * 내 정보, 후원 이력 조회
     */
    @GetMapping
    public String getMyPage(){
        return "ok";
    }

    @PostMapping
    public String editMyPage(){
        return "ok";
    }

    /**
     * 클라이언트 전달 정보 : 회원 유형
     * 일반 사용자 : 이름, 번호, 이메일, 패스워드, 닉네임, 프로필 사진
     * 단체 사용자 : 단체이름, 번호, 이메일, 패스워드, 닉네임, 프로필 사진 -> 승인 후 가입
     */
    @PutMapping
    public String newUser(@ModelAttribute NewUserForm newUserForm){
        return "ok";
    }

    /**
     * 클라이언트 전송 : 이메일, 패스워드
     * 서버 응답 : 세션 아이디, fail
     */
    @PostMapping("/login")
    public String login(@RequestParam String id,
                        @RequestParam String password){
        return "ok";
    }

    /**
     * 클라이언트 : 사용자 id, 포인트 충전 금액
     * 서버 응답 : success fail
     */
    @PostMapping("/pay")
    public String payComplete(){
        return "ok";
    }
}
