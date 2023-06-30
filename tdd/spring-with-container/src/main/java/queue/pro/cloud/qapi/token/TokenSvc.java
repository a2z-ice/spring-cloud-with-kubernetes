package queue.pro.cloud.qapi.token;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class TokenSvc {
    @GetMapping("/tokens")
    Flux<Integer> getTokens(){
        return Flux.just(1,2,3);
    }

}
