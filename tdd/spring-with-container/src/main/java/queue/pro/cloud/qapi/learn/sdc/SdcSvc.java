package queue.pro.cloud.qapi.learn.sdc;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import queue.pro.cloud.qapi.sdc.SdcInfoEntity;
import queue.pro.cloud.qapi.sdc.SdcInfoRepo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@AllArgsConstructor
@Service
public class SdcSvc {

    private final SdcInfoRepo sdcInfoRepo;
    public Flux<SdcInfoEntity> getSdcInfoList(Pageable page) {
        return Flux.empty();
    }
    public Mono<Page<SdcInfoEntity>> getSdcInfoPage(Pageable page) {
        return Mono.just(sdcInfoRepo.findAll(page))
                .subscribeOn(Schedulers.boundedElastic());
    }
}
