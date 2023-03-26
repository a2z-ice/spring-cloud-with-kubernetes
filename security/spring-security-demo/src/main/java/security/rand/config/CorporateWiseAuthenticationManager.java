package security.rand.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;


@Slf4j
@AllArgsConstructor
@Component
public class CorporateWiseAuthenticationManager implements AuthorizationManager<RequestAuthorizationContext> {
    private final ContextHolder contextHolder;

    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext requestAuthorizationContext) {
        AuthorizationManager.super.verify(authentication, requestAuthorizationContext);
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext requestAuthorizationContext) {
        String corporateId =  requestAuthorizationContext.getVariables().get("corporateId");
        String serviceTag =  requestAuthorizationContext.getVariables().get("serviceTag");
        if(corporateId == null || serviceTag == null ) return new AuthorizationDecision(false);

        if(((Authentication) authentication.get()) instanceof JwtAuthenticationToken jwt){
//            String sub = (String) jwt.getToken().getClaims().get("sub");
            if("123".equals(corporateId)){
                contextHolder.putCorporateId(corporateId);
                contextHolder.putServiceTag(serviceTag);
                return  new AuthorizationDecision(true);
            }
        }
        return new AuthorizationDecision(false);
    }

}
