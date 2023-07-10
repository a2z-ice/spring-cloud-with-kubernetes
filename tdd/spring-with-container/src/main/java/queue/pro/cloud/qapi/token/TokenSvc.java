package queue.pro.cloud.qapi.token;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import queue.pro.cloud.qapi.sdc.SdcInfoEntity;
import queue.pro.cloud.qapi.sdc.SdcInfoRepo;
import queue.pro.cloud.qapi.token.repo.TokenRepo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Optional;

@AllArgsConstructor
@Service
public class TokenSvc {

    private final TokenRepo tokenRepo;
    private final SdcInfoRepo sdcInfoRepo;

    public Mono<TokenEntity> getTokenById(String tokenId){
        return Mono.fromSupplier(() -> tokenRepo.findById(tokenId).orElseThrow())
                .subscribeOn(Schedulers.boundedElastic());
    }
    public Flux<TokenEntity> getTokenAllOrderByIdDesc(){
        return Flux.fromStream(() -> this.tokenRepo.findAll(Sort.by(Sort.Direction.DESC,"id")).stream())
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Optional<SdcInfoEntity>> getLogInCounterUserToken(String loginUserId) {
        return Mono.
                fromSupplier(
                        () -> sdcInfoRepo.findByServingUserLoginId(loginUserId))
                .subscribeOn(Schedulers.boundedElastic());
    }

    private void getLoginCounterInfo(String loginUserId) {

    }
}
