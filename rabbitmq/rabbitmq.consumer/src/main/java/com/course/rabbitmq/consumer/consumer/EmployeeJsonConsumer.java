package com.course.rabbitmq.consumer.consumer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.course.rabbitmq.consumer.entity.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;

//@Service
public class EmployeeJsonConsumer {

	@Autowired
	private ObjectMapper objectMapper;
	
	private static final Logger LOG = LoggerFactory.getLogger(EmployeeJsonConsumer.class);
	
	@RabbitListener(queues = "course.employee")
	public void listen(String message) throws IOException {
		var employee = objectMapper.readValue(message, Employee.class);
		
		LOG.info("Employee is {}", employee);
	}
	
}
