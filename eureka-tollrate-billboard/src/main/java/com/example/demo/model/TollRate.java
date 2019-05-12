package com.example.demo.model;

import java.math.BigDecimal;

public class TollRate {
	
	private int stationId;
	private BigDecimal currentRate;
	private String timestamp;
	
	public TollRate() {}
	
	public TollRate(int stationId, BigDecimal currentRate, String timestamp) {
		this.stationId = stationId;
		this.currentRate = currentRate;
		this.timestamp = timestamp;
	}

	public int getStationId() {
		return stationId;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	public BigDecimal getCurrentRate() {
		return currentRate;
	}

	public void setCurrentRate(BigDecimal currentRate) {
		this.currentRate = currentRate;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
