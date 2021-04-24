package com.example.demo;

import org.springframework.stereotype.Component;


public class SimpleObj {
	private final String uuid;
	public SimpleObj(String uuid) {
		this.uuid = uuid;
	}

	public String getUuid() {
		return uuid;
	}
	
	

}
