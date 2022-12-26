package com.course.rabbitmq.consumer.consumer;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.course.rabbitmq.consumer.entity.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;

//@Service
public class SpringEmployeeConsumer {

	private static final Logger LOG = LoggerFactory.getLogger(SpringEmployeeConsumer.class);
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@RabbitListener(queues = "q.spring2.accounting.work")
	public void listenAccounting(String message) throws IOException {
		var employee = objectMapper.readValue(message, Employee.class);
		LOG.info("On accounting, consuming {}", employee);
		
		if (StringUtils.isEmpty(employee.getName())) {
			throw new IllegalArgumentException("Name is empty");
		}
		
		LOG.info("On accounting, employee is {}", employee);
	}
	
	@RabbitListener(queues = "q.spring2.marketing.work")
	public void listenMarketing(String message) throws IOException {
		var employee = objectMapper.readValue(message, Employee.class);
		LOG.info("On marketing, consuming {}", employee);				
		LOG.info("On marketing, employee is {}", employee);		
	}
	
}
