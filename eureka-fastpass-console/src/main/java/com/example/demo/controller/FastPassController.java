package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.example.demo.ConfigurationBean;
import com.example.demo.model.FastPassCustomer;

//@RibbonClient(name="pluralsight-fastpass-service", configuration=TollClientRoutingConfig.class)
@Controller
public class FastPassController {
	
	
	private final RestTemplate restTemplate;
	
	@Autowired
	private ConfigurationBean config;
	
	
	public FastPassController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
//	@HystrixCommand(fallbackMethod="getFastPassCustomerDetailsBackup")
	@RequestMapping(path="/customerdetails", params={"fastpassid"})
	public String getFastPassCustomerDetails(@RequestParam String fastpassid, Model m) {
		
		FastPassCustomer c = restTemplate.getForObject(config.getFastPassServiceLocation() + "?fastpassid=" +fastpassid, FastPassCustomer.class);
		System.out.println("retrieved customer details");
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
