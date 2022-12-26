package com.course.rabbitmq.consumer.consumer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.course.rabbitmq.consumer.entity.Picture;
import com.fasterxml.jackson.databind.ObjectMapper;

//@Service
public class PictureTwoConsumer {

	private static final Logger LOG = LoggerFactory.getLogger(PictureTwoConsumer.class);

	@Autowired
	private ObjectMapper objectMapper;

	@RabbitListener(queues = { "q.picture.image", "q.picture.vector", "q.picture.log", "q.picture.filter" })
	public void listen(Message messageAmqp) throws IOException {
		var jsonString = new String(messageAmqp.getBody());
		var picture = objectMapper.readValue(jsonString, Picture.class);
		LOG.info("Consuming picture : {} with routing key : {}", picture,
				messageAmqp.getMessageProperties().getReceivedRoutingKey());
	}

}
