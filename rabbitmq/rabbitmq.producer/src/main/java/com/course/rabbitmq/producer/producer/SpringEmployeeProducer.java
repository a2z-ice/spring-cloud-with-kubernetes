package com.course.rabbitmq.producer.producer;

import java.io.IOException;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.rabbitmq.producer.entity.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SpringEmployeeProducer {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	public void sendMessage(Employee data) throws IOException {
		var json = objectMapper.writeValueAsString(data);
		
		rabbitTemplate.convertAndSend("x.spring2.work", "", json);
	}
	
}
