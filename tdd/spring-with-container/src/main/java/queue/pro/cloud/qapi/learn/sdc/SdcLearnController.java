package queue.pro.cloud.qapi.learn.sdc;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import queue.pro.cloud.qapi.sdc.SdcInfoEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/learn")
public class SdcLearnController {
    private final SdcSvc sdcSvc;
    @GetMapping("/sdc")
    public Flux<SdcInfoEntity> getSdcInfoList(
            @RequestParam(value="page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size                                              ){
        return sdcSvc.getSdcInfoList(PageRequest.of(page,size));
    }

    @GetMapping("/sdc-page")
    public Mono<Page<SdcInfoEntity>> getSdcInfoPage(
            @RequestParam(value="page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size                                              ){
        return sdcSvc.getSdcInfoPage(PageRequest.of(page,size));
    }
}
