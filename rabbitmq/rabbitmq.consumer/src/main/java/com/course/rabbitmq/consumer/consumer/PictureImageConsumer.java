package com.course.rabbitmq.consumer.consumer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.course.rabbitmq.consumer.entity.Picture;
import com.fasterxml.jackson.databind.ObjectMapper;

//@Service
public class PictureImageConsumer {

	private static final Logger LOG = LoggerFactory.getLogger(PictureImageConsumer.class);
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@RabbitListener(queues = "q.picture.image")
	public void listen(String message) throws IOException {
		var picture = objectMapper.readValue(message, Picture.class);
		LOG.info("On image : {}", picture);
	}
	
}
