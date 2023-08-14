package queue.pro.cloud.qapi.common;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import queue.pro.cloud.qapi.bean.LoginUserInfoBean;
import reactor.core.publisher.Mono;

public class UserContext {

    public static Mono<LoginUserInfoBean> loginUserInfoBean() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Mono.fromSupplier(() -> new LoginUserInfoBean("preferred_username","",""));
    }
}
