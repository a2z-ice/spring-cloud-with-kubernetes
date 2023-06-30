package queue.pro.cloud.qapi.token;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/v1")
public class TokenController {
    @GetMapping("/tokens")
    Flux<Integer> getTokens(){
        return Flux.just(1,2,3).log();
    }
}
