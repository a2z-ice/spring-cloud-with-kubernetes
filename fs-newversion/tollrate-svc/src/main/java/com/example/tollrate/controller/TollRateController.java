package com.example.tollrate.controller;

import com.example.tollrate.model.TollRate;
import com.example.tollrate.service.TollRateServcie;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor
@Log
public class TollRateController {

    TollRateServcie tollRateServcie;

    @GetMapping("/tollrate/{stationId}")
    public TollRate getTollRate(@PathVariable int stationId, HttpServletRequest request){

        log.info("Requested port number: " + request.getRemotePort() + " local port:" + request.getLocalPort());
        return tollRateServcie.getTollRateByStationId(stationId,request.getLocalPort());
    }
}
