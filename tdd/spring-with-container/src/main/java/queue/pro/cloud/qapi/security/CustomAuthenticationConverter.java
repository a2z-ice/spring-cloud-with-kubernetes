package queue.pro.cloud.qapi.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import reactor.core.publisher.Mono;

public class CustomAuthenticationConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {
    public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {
        return Mono.just(jwt).map(this::doConversion);
    }

    private AbstractAuthenticationToken doConversion(Jwt jwt) {

        return new JwtAuthenticationToken( jwt, getAuthorities(jwt));
    }

    private List<GrantedAuthority> getAuthorities(Jwt jwt){
        Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");
        if(realmAccess == null || realmAccess.isEmpty()){
            return new ArrayList<>();
        }

        return ((List<String>)realmAccess.get("roles")).stream()
                .map(role -> "ROLE_"+role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}