package com.example.tollrate.service;

import com.example.tollrate.model.TollRate;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

@Service
@Log
public class TollRateServcie {
    public TollRate getTollRateByStationId(int stationId, int port) {
        log.info("Requested stationId:" + stationId);

        TollRate tr;

        System.out.println("Station requested: " + stationId);

        switch(stationId) {
            case 1:
                tr = new TollRate(stationId, new BigDecimal("0.55"), Instant.now().toString(), port);
                break;
            case 2:
                tr = new TollRate(stationId, new BigDecimal("1.05"), Instant.now().toString(), port);
                break;
            case 3:
                tr = new TollRate(stationId, new BigDecimal("0.60"), Instant.now().toString(), port);
                break;
            default:
                tr = new TollRate(stationId, new BigDecimal("1.00"), Instant.now().toString(), port);
                break;
        }

        return tr;

    }
}
