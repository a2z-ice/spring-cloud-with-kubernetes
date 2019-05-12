package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.TollRate;

@Controller
public class DashboardController {
	
	
	private RestTemplate restTemplate;
	
	public DashboardController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

//	@HystrixCommand(fallbackMethod="getTollRateBackup") 
	@RequestMapping("/dashboard")
	public String GetTollRate(@RequestParam int stationId, Model m) {
		
		TollRate tr = restTemplate.getForObject("http://eureka-tollrate-service/tollrate/" + stationId, TollRate.class);
		System.out.println("stationId: " + stationId);
		m.addAttribute("rate", tr.getCurrentRate());
		return "dashboard";
	}
	
	public String getTollRateBackup(@RequestParam int stationId, Model m) { 
		System.out.println("Fallback operation called");
		m.addAttribute("rate", "1.00");
		return "dashboard";
	}

}
