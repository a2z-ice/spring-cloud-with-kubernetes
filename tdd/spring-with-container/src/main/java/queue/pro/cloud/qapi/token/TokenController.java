package queue.pro.cloud.qapi.token;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1")
public class TokenController {
    @GetMapping("/tokens")
    Flux<Integer> getTokens(){
        return Flux.just(1,2,3).log();
    }

    @GetMapping("/token/{id}")
    Mono<Integer> getTokens(@PathVariable("id") Integer id){
        return Mono.just(1);
    }
}
