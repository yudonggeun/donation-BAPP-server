package com.bapp.donationserver.intercepter;

import com.bapp.donationserver.data.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    private final static String errorMsg = "로그인 이후 사용 가능합니다.";
    private final static String successMsg = "정상 로그인 사용자입니다.";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        boolean isMember = request.getSession(false) != null;

        if (request.getSession(false) == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, errorMsg);
            log.info("{} url={}", errorMsg, request.getRequestURL());
            return isMember;
        }

        log.info("{} url={}", successMsg, request.getRequestURL());

        return true;
    }
}
