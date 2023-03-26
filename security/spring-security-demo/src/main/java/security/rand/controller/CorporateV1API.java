package security.rand.controller;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RestController
@RequestMapping("v1/service/{serviceTag}/corporate/{corporateId}")
public @interface CorporateV1API {
    @AliasFor(annotation = Component.class)
    String value() default "";
}