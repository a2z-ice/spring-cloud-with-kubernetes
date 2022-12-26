package com.course.rabbitmq.consumer.consumer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.course.rabbitmq.consumer.entity.Picture;
import com.fasterxml.jackson.databind.ObjectMapper;

//@Service
public class SpringPictureConsumer {

	private static final Logger LOG = LoggerFactory.getLogger(SpringPictureConsumer.class);

	@Autowired
	private ObjectMapper objectMapper;

	@RabbitListener(queues = "q.spring.image.work")
	public void listenImage(String message) throws IOException {
		var picture = objectMapper.readValue(message, Picture.class);
		LOG.info("Consuming image {}", picture.getName());

		if (picture.getSize() > 9000) {
			throw new IllegalArgumentException("Image too large : " + picture.getName());
		}

		LOG.info("Processing image : " + picture.getName());
	}

	@RabbitListener(queues = "q.spring.vector.work")
	public void listenVector(String message) throws IOException {
		var picture = objectMapper.readValue(message, Picture.class);
		LOG.info("Consuming vector {}", picture.getName());
		LOG.info("Processing vector : " + picture.getName());
	}

}
