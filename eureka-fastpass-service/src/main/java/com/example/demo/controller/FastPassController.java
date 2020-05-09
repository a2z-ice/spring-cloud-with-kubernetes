package com.example.demo.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.FastPassCustomer;

@RestController
public class FastPassController {
	
	List<FastPassCustomer> customerlist = new ArrayList<FastPassCustomer>();
	
	public FastPassController() {
		
		FastPassCustomer fc1 = new FastPassCustomer("100", "Richard Seroter", "555-123-4567", new BigDecimal("19.50"));
		FastPassCustomer fc2 = new FastPassCustomer("101", "Jason Salmond", "555-321-7654", new BigDecimal("11.25"));
		FastPassCustomer fc3 = new FastPassCustomer("102", "Lisa Szpunar", "555-987-6543", new BigDecimal("35.00"));
		
		customerlist.add(fc1);
		customerlist.add(fc2);
		customerlist.add(fc3);
	}
	
	@RequestMapping(path="/fastpass", params={"fastpassid"})
	public FastPassCustomer getFastPassById(@RequestParam String fastpassid, HttpServletRequest request) {
		
		Predicate<FastPassCustomer> p = c-> c.getFastPassId().equals(fastpassid);
		FastPassCustomer customer = customerlist.stream().filter(p).findFirst().get();
		System.out.println("customer details retrieved");
//		if(true) throw new RuntimeException("Internal server error");
		return customer;
	}
	
	@RequestMapping(path="/fastpass", params={"fastpassphone"})
	public FastPassCustomer getFastPassByPhone(@RequestParam String fastpassphone) {
		
		Predicate<FastPassCustomer> p = c-> c.getFastPassPhone().equals(fastpassphone);
		FastPassCustomer customer = customerlist.stream().filter(p).findFirst().get();
		System.out.println("customer details retrieved");
		return customer;
	}

}
