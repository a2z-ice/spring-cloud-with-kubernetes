package queue.pro.cloud.qapi.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import queue.pro.cloud.qapi.token.repo.TokenRepo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Optional;

@AllArgsConstructor
@Service
public class TokenSvc {

    private final TokenRepo tokenRepo;

    public Mono<TokenEntity> getTokenById(String tokenId){
        return Mono.fromSupplier(() -> tokenRepo.findById(tokenId).orElseThrow())
                .subscribeOn(Schedulers.boundedElastic());
    }



    public Flux<TokenEntity> getTokenAll(){
        return Flux.fromStream(() -> this.tokenRepo.findAll().stream())
                .subscribeOn(Schedulers.boundedElastic());
    }

}
