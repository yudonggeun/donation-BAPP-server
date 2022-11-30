package com.bapp.donationserver.intercepter;

import com.bapp.donationserver.data.type.MemberType;
import com.bapp.donationserver.data.consts.SessionConst;
import com.bapp.donationserver.data.dto.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class AdminInterceptor implements HandlerInterceptor {

    private final static String  errorMsg = "해당 기능은 관리자만이 사용가능합니다.";
    private final static String  successMsg = "관리자 사용자입니다.";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        MemberDto member = (MemberDto) request.getSession(false).getAttribute(SessionConst.LOGIN_MEMBER);
        boolean isAdmin = member.getMemberType() == MemberType.ADMIN;

        if(!isAdmin){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, errorMsg);
        }

        log.info("AdminInterceptor : {}", isAdmin ? successMsg : errorMsg);
        return isAdmin;
    }
}
