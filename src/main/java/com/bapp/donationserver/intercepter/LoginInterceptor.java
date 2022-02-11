package com.bapp.donationserver.intercepter;

import com.bapp.donationserver.data.SessionConst;
import com.bapp.donationserver.data.dto.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        boolean isMember = request.getSession(false) != null;

        log.info("Login interceptor : {}", isMember ? "정상 로그인 사용자입니다." : "로그인 이후 사용 가능합니다.");
        return isMember;
    }
}
