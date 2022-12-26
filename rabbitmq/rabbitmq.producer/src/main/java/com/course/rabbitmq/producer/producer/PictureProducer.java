package com.course.rabbitmq.producer.producer;

import java.io.IOException;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.rabbitmq.producer.entity.Picture;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PictureProducer {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	public void sendMessage(Picture picture) throws IOException {
		var json = objectMapper.writeValueAsString(picture);
		rabbitTemplate.convertAndSend("x.picture", picture.getType(), json);
	}
	
}
