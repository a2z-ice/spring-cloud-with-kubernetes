package com.course.rabbitmq.producer.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RabbitmqQueue {

	private long messages;
	
	private String name;

	public long getMessages() {
		return messages;
	}

	public String getName() {
		return name;
	}

	public void setMessages(long messages) {
		this.messages = messages;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "RabbitmqQueue [messages=" + messages + ", name=" + name + "]";
	}
	
	public boolean isDirty() {
		return messages > 0;
	}
	
}
