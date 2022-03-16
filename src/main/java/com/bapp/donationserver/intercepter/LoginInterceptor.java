package com.bapp.donationserver.intercepter;

import com.bapp.donationserver.data.consts.HttpAttributeConst;
import com.bapp.donationserver.data.consts.SessionConst;
import com.bapp.donationserver.exception.IllegalUserDataException;
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

        try{
            log.info("{} url={} member={}", successMsg, request.getRequestURL(), request.getSession(false).getAttribute(SessionConst.LOGIN_MEMBER));
        } catch (NullPointerException e){
            throw new IllegalUserDataException("올바르지 않은 세션 요청입니다.");
        }

        return true;
    }
}
