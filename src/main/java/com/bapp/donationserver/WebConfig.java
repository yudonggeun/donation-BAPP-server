package com.bapp.donationserver;

import com.bapp.donationserver.intercepter.AdminInterceptor;
import com.bapp.donationserver.intercepter.CharityInterceptor;
import com.bapp.donationserver.intercepter.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * 회원 가입한 유저만 사용 가능
         * 세션 여부 확인 -> 로그인 검증
         */
        registry.addInterceptor(new LoginInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/api/new/**", "/error");
        /**
         * 관리자 권한을 가지고 있는 유저만이 사용가능한 api 권한 검증
         */
        registry.addInterceptor(new AdminInterceptor())
                .order(2)
                .addPathPatterns("/api/admin/**");
        /**
         * 단체 멤버 사용 api
         * 일반 유저는 사용 금지 api 권한 검증
         */
        registry.addInterceptor(new CharityInterceptor())
                .order(3)
                .addPathPatterns("/api/charity/**");
    }
}
