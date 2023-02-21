package com.example.l2cache.anotherpackage.annotation;

import com.example.l2cache.aspect.annotation.PointcutPlaceHolderAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AnotherAspectAnnotationTest {
    @PointcutPlaceHolderAnnotation
    public void anotherAspect(){
        log.info("another aspect.................");
    }
}
