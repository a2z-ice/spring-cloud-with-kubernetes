package com.course.rabbitmq.two.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PaymentCancelStatus {

	private boolean cancelStatus;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate cancelDate;

	private String invoiceNumber;

	public PaymentCancelStatus() {

	}

	public PaymentCancelStatus(boolean cancelStatus, LocalDate cancelDate, String invoiceNumber) {
		super();
		this.cancelStatus = cancelStatus;
		this.cancelDate = cancelDate;
		this.invoiceNumber = invoiceNumber;
	}

	public LocalDate getCancelDate() {
		return cancelDate;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public boolean isCancelStatus() {
		return cancelStatus;
	}

	public void setCancelDate(LocalDate cancelDate) {
		this.cancelDate = cancelDate;
	}

	public void setCancelStatus(boolean cancelStatus) {
		this.cancelStatus = cancelStatus;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	@Override
	public String toString() {
		return "PaymentCancelStatus [cancelStatus=" + cancelStatus + ", cancelDate=" + cancelDate + ", invoiceNumber="
				+ invoiceNumber + "]";
	}

}
