package security.rand.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WebSecurity {
    public boolean check(Authentication authentication, RequestAuthorizationContext requestAutContext) {
        String corporateId = requestAutContext.getVariables().get("corporateId");
        log.info(corporateId);
        return true;
    }
}