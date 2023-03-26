package security.rand.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class WebSecurity {
    public boolean check(Authentication authentication, HttpServletRequest request) {
				return true;
    }
}