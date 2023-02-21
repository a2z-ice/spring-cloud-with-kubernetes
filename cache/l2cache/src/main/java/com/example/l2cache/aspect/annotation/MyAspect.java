package com.example.l2cache.aspect.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MyAspect {

    @Pointcut("@annotation(com.example.l2cache.aspect.annotation.MyAnnotation)")
    public void myAnnotationPointcut() {}

    @Before("myAnnotationPointcut()")
    public void beforeMyAnnotationMethod(JoinPoint joinPoint) {
        System.out.println("Before method " + joinPoint.getSignature().getName());
    }

    @After("myAnnotationPointcut()")
    public void afterMyAnnotationMethod(JoinPoint joinPoint) {
        System.out.println("After method " + joinPoint.getSignature().getName());
    }
}
