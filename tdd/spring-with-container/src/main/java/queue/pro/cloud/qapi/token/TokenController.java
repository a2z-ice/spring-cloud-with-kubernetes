package queue.pro.cloud.qapi.token;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/v1")
public class TokenController {
    private final TokenSvc tokenSvc;
    @GetMapping("/tokens")
    Flux<TokenEntity> getTokens(){
        return tokenSvc.getTokenAll();
    }

    @GetMapping("/token/{id}")
    Mono<TokenEntity> getToken(@PathVariable("id") String id){
        return tokenSvc.getTokenById(id);
    }

}
