package com.example.l2cache.aspect.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AnnotationBasedAspect {

    @Pointcut("@annotation(com.example.l2cache.aspect.annotation.PointcutPlaceHolderAnnotation)")
    public void annotationBasedPointcut() {}

    @Before("annotationBasedPointcut()")
    public void beforeAnnotationMethod(JoinPoint joinPoint) {
        System.out.println("Before method " + joinPoint.getSignature().getName());
    }

    @After("annotationBasedPointcut()")
    public void afterAnnotationMethod(JoinPoint joinPoint) {
        System.out.println("After method " + joinPoint.getSignature().getName());
    }
}
