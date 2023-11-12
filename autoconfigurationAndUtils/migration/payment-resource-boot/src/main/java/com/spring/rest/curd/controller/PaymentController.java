package com.spring.rest.curd.controller;

import com.spring.rest.curd.dto.PaymentResponse;
import com.spring.rest.curd.model.Payment;
import com.spring.rest.curd.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paymentResource")
public class PaymentController {
	@Autowired
	private PaymentService service;

	@PostMapping("/payNow")
	public PaymentResponse payInstant(@RequestBody Payment payment) {
		return service.pay(payment);
	}

	@GetMapping("/getTransactionByVendor/{vendor}")
	public PaymentResponse getTransaction(@PathVariable String vendor) {
		return service.getTx(vendor);
	}

}
