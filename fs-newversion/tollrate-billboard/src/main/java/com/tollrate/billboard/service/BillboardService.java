package com.tollrate.billboard.service;

import com.tollrate.billboard.config.BillboardConfig;
import com.tollrate.billboard.model.TollRate;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
@Log
public class BillboardService {
    private final BillboardConfig billboardConfig;
    private final RestTemplate restTemplate;
    private final WebClient.Builder webClientBuilder;

    public TollRate getStationWiseTollRate(int stationId){
        log.info("Given station id : " + stationId);
        return restTemplate.getForObject(billboardConfig.getTollRateSvcUrl() + stationId, TollRate.class);

    }

    public TollRate getStationWiseTollRateUsingWebClient(int stationId){
        log.info("Given station id : " + stationId);
        TollRate tollRate = webClientBuilder.build()
                .get().uri(billboardConfig.getTollRateSvcUrl()+stationId)
                .retrieve()
                .bodyToMono(TollRate.class)
                .block();
        ;
        return tollRate;

    }
}
