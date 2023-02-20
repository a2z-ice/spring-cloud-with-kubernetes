package com.example.l2cache.jwt;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
@Aspect
public class AuthAspect {
    private final OAuthClientExample oAuthClientExample;
    private final ContextHolder contextHolder;
    @Before("execution(* com.example.l2cache.jwt.AspectTestClass.printJwtValue(..))")
    public void setJwt(){

        contextHolder.put(oAuthClientExample.getJwt());
    }

    @After("execution(* com.example.l2cache.jwt.AspectTestClass.printJwtValue(..))")
    public void clearJwt(){
        log.info("After aspect calling");
        ContextHolder.clearContext();
    }

}
