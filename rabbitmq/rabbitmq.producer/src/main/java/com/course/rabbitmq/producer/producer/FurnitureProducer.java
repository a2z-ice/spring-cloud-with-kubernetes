package com.course.rabbitmq.producer.producer;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.rabbitmq.producer.entity.Furniture;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class FurnitureProducer {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	public void sendMessage(Furniture furniture) throws IOException {
		var messageProperties = new MessageProperties();
		messageProperties.setHeader("color", furniture.getColor());
		messageProperties.setHeader("material", furniture.getMaterial());
		
		var json = objectMapper.writeValueAsString(furniture);
		
		var message = new Message(json.getBytes(), messageProperties);
		
		rabbitTemplate.send("x.promotion", "", message);
	}
	
}
