package queue.pro.cloud.qapi.token;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import queue.pro.cloud.qapi.sdc.SdcInfoEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/v1")
public class TokenController {
    private final TokenSvc tokenSvc;
    @GetMapping("/token")
    Flux<TokenEntity> getTokens(){
        return tokenSvc.getTokenAllOrderByIdDesc();
    }

    @GetMapping("/token/{id}")
    Mono<TokenEntity> getToken(@PathVariable("id") String id){
        return tokenSvc.getTokenById(id);
    }

    @GetMapping("/token/sdc-user")
    Mono<?> getCounterToken(@AuthenticationPrincipal Mono<Jwt> jwtPrincipal){
        return jwtPrincipal
                .map(jwt -> jwt.getClaimAsString("preferred_username"))
                .flatMap(tokenSvc::getLogInCounterUserToken)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());

    }
}
