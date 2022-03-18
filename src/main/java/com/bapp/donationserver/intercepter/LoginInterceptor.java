package com.bapp.donationserver.intercepter;

import com.bapp.donationserver.data.consts.HttpAttributeConst;
import com.bapp.donationserver.data.consts.SessionConst;
import com.bapp.donationserver.exception.IllegalUserDataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    private final static String errorMsg = "로그인 이후 사용 가능합니다.";
    private final static String successMsg = "로그인 사용자입니다.";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            throw new IllegalUserDataException(errorMsg);
        }

        log.info("{} url={} member={}", successMsg, request.getRequestURL(), session.getAttribute(SessionConst.LOGIN_MEMBER));

        return true;
    }
}
