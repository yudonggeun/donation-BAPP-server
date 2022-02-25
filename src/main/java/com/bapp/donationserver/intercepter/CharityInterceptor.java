package com.bapp.donationserver.intercepter;

import com.bapp.donationserver.data.type.MemberType;
import com.bapp.donationserver.data.SessionConst;
import com.bapp.donationserver.data.dto.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CharityInterceptor implements HandlerInterceptor {

    private final static String errorMsg = "해당 기능은 관리자만이 사용가능합니다.";
    private final static String successMsg = "관리자 사용자입니다.";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        MemberDto member = (MemberDto) request.getSession(false).getAttribute(SessionConst.LOGIN_MEMBER);

        boolean isNormalUser = member.getMemberType() == MemberType.USER;

        if (isNormalUser) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, errorMsg);
        }

        log.info("{}", isNormalUser ? errorMsg : successMsg);

        return !isNormalUser;
    }
}
