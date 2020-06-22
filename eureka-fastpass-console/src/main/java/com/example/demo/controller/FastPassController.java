package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.ConfigurationBean;
import com.example.demo.model.FastPassCustomer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpHeaders;

//@RibbonClient(name="pluralsight-fastpass-service", configuration=TollClientRoutingConfig.class)
@Controller
public class FastPassController {
	
	
	private final RestTemplate restTemplate;
	
	@Autowired
	private ConfigurationBean config;
	
	private final WebClient webClientInstance;
	public FastPassController(RestTemplate restTemplate,WebClient webClientInstance) {
		this.restTemplate = restTemplate;
		this.webClientInstance = webClientInstance;
	}
	
//	@HystrixCommand(fallbackMethod="getFastPassCustomerDetailsBackup")
	@RequestMapping(path="/customerdetails", params={"fastpassid"})
	public String getFastPassCustomerDetails(@RequestParam String fastpassid, Model m) {
		
		FastPassCustomer c = restTemplate.getForObject(config.getFastPassServiceLocation() + "?fastpassid=" +fastpassid, FastPassCustomer.class);
		System.out.println("retrieved customer details");
		m.addAttribute("customer", c);
		
		return "console";
	}
	@RequestMapping(path="/webclient", params={"fastpassid"})
	public String getServiceUsingWebClient(@RequestParam String fastpassid, Model m) {
		FastPassCustomer c = webClientInstance
                .get()
                .uri(config.getFastPassServiceLocation() + "?fastpassid=" +fastpassid)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(FastPassCustomer.class)
                .block();
		m.addAttribute("customer", c);
		return "console";
		
	}
	
	public String getFastPassCustomerDetailsBackup(@RequestParam String fastpassid, Model m) {
		
		FastPassCustomer c = new FastPassCustomer();
		c.setFastPassId(fastpassid);
		System.out.println("Fallback operation called");
		m.addAttribute("customer", c);
		return "console";
	}

}
