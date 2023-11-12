package com.spring.rest.curd.dao;

import com.spring.rest.curd.model.Payment;

import java.util.List;

public interface PaymentDao {
	
	public String payNow(Payment payment);
	
	public List<Payment> getTransactionInfo(String vendor);

}
