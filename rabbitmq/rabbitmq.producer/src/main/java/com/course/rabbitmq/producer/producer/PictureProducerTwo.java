package com.course.rabbitmq.producer.producer;

import java.io.IOException;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.rabbitmq.producer.entity.Picture;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PictureProducerTwo {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	public void sendMessage(Picture picture) throws IOException {
		var json = objectMapper.writeValueAsString(picture);
		
		var sb = new StringBuilder();
		
		// 1st word is picture source
		sb.append(picture.getSource());
		sb.append(".");
		
		// 2nd word is based on picture size
		if (picture.getSize() > 4000) {
			sb.append("large");
		} else {
			sb.append("small");
		}
		sb.append(".");
		
		// 3rd word is picture type
		sb.append(picture.getType());
		
		rabbitTemplate.convertAndSend("x.picture2", sb.toString(), json);
	}
	
}
