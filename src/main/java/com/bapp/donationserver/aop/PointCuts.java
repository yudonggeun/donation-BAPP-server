package com.bapp.donationserver.aop;

import org.aspectj.lang.annotation.Pointcut;

public class PointCuts {

    @Pointcut("execution(* com.bapp.donationserver.controller..*Controller.*(..))")
    public void allController() {
    }

    @Pointcut("execution(* com.bapp.donationserver.service..*(..))")
    public void allService() {
    }

    @Pointcut("execution(* com.bapp.donationserver.repository..*(..))")
    public void allRepository() {
    }

    @Pointcut("execution(* com.bapp.donationserver.blockchain..*(..))")
    public void allBlockChain() {
    }

    @Pointcut("execution(* com.bapp.donationserver..*(..))")
    public void all() {
    }

}
