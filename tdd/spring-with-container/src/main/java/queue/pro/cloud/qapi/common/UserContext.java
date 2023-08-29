package queue.pro.cloud.qapi.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import queue.pro.cloud.qapi.bean.LoginUserInfoBean;
import queue.pro.cloud.qapi.error.NotFoundException;
import reactor.core.publisher.Mono;

@Slf4j
public class UserContext {

    public static Mono<LoginUserInfoBean> loginUserInfoBean() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .handle((auth, sink) -> {
                    Jwt principal = (Jwt) auth.getPrincipal();
                    Object username = principal.getClaims().get("preferred_username");
                    log.info("Type of auth principal: " + auth.getPrincipal());
                    if(username == null || username.toString().isEmpty()) {
                        sink.error(new NotFoundException(new NotFoundException.Reason("missing", "User is missing")));
                        return;
                    }
                    sink.next(new LoginUserInfoBean(username.toString(), "", ""));
                });
    }
}
