package com.bapp.donationserver.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class AspectAdvice {

    @Before("com.bapp.donationserver.aop.PointCuts.all()")
    public void doBefore(JoinPoint joinPoint){
        log.info("[before] 메서드 호출 {}", joinPoint.getSignature());
    }

    @AfterReturning("com.bapp.donationserver.aop.PointCuts.all()")
    public void doAfterReturning(JoinPoint joinPoint){
        log.info("[After Return] 메서드 정상 종료 {}", joinPoint.getSignature());
    }

    @AfterThrowing(value = "com.bapp.donationserver.aop.PointCuts.all()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e){
        log.error("[After Throwing] 에러 발생 : {} At {}", e.getMessage(), joinPoint.getSignature());
    }
}
