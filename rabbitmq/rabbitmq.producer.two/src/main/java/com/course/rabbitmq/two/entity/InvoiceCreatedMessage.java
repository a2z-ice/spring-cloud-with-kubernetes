package com.course.rabbitmq.two.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class InvoiceCreatedMessage {

	private double amount;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate createdDate;

	private String currency;

	private String invoiceNumber;

	public InvoiceCreatedMessage() {

	}

	public InvoiceCreatedMessage(double amount, LocalDate createdDate, String currency, String invoiceNumber) {
		super();
		this.amount = amount;
		this.createdDate = createdDate;
		this.currency = currency;
		this.invoiceNumber = invoiceNumber;
	}

	public double getAmount() {
		return amount;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public String getCurrency() {
		return currency;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	@Override
	public String toString() {
		return "InvoiceCreatedMessage [amount=" + amount + ", createdDate=" + createdDate + ", currency=" + currency
				+ ", invoiceNumber=" + invoiceNumber + "]";
	}

}
