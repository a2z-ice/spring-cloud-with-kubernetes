package com.tollrate.billboard.controller;

import com.tollrate.billboard.model.TollRate;
import com.tollrate.billboard.service.BillboardService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
@Log
@RequestMapping("/dashboard")
public class BillboardController {

    final private BillboardService billboardService;

    @RequestMapping("/resttemplate")
    public String getTollRateRestTemplate(@RequestParam(required = true) int stationId, Model model, HttpServletRequest request){
        log.info("given station id is :" + stationId + " request port:" + request.getRemotePort());
        TollRate tollRate = billboardService.getStationWiseTollRate(stationId);
        model.addAttribute("rate", tollRate.getCurrentRate());
        model.addAttribute("port", tollRate.getServicePort());
        return "dashboard";
    }    @RequestMapping("/webclient")
    public String getTollRateWebClient(@RequestParam(required = true) int stationId, Model model, HttpServletRequest request){
        log.info("given station id is :" + stationId + " request port:" + request.getRemotePort());
        TollRate tollRate = billboardService.getStationWiseTollRateUsingWebClient(stationId);
        model.addAttribute("rate", tollRate.getCurrentRate());
        model.addAttribute("port", tollRate.getServicePort());
        return "dashboard";
    }
}
