package com.bapp.donationserver.intercepter;

import com.bapp.donationserver.data.Member;
import com.bapp.donationserver.data.type.MemberType;
import com.bapp.donationserver.data.consts.SessionConst;
import com.bapp.donationserver.exception.IllegalUserDataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CharityInterceptor implements HandlerInterceptor {

    private final static String errorMsg = "해당 기능은 관리자 또는 기부 단체 사용자만이 사용가능합니다.";
    private final static String successMsg = "charity 서비스 사용자입니다.";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        Member member = (Member) request.getSession(false).getAttribute(SessionConst.LOGIN_MEMBER);

        boolean isCorrectUser = member.getMemberType() != MemberType.USER;

        if (!isCorrectUser) {
            throw new IllegalUserDataException(errorMsg);
        }

        log.info("{}", successMsg);

        return true;
    }
}
