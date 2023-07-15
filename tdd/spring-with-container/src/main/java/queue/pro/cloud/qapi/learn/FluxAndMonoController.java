package queue.pro.cloud.qapi.learn;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class FluxAndMonoController {
    @GetMapping("/flux")
    Flux<Integer> flux(){
        return Flux.just(1,2,3).log();
    }

    @GetMapping("/mono")
    Mono<String> mono(@AuthenticationPrincipal Mono<Jwt> jwtPrincipal){
        return jwtPrincipal.map(Jwt::getSubject).log();
    }
}
