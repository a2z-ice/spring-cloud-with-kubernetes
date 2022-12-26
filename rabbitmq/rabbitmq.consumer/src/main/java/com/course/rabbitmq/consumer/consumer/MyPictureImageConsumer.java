package com.course.rabbitmq.consumer.consumer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;

import com.course.rabbitmq.consumer.entity.Picture;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;

//@Service
public class MyPictureImageConsumer {

	private static final Logger LOG = LoggerFactory.getLogger(MyPictureImageConsumer.class);

	@Autowired
	private ObjectMapper objectMapper;

	@RabbitListener(queues = "q.mypicture.image")
	public void listen(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
		var picture = objectMapper.readValue(message, Picture.class);

		if (picture.getSize() > 9000) {
			channel.basicReject(tag, false);
			return;
//			throw new AmqpRejectAndDontRequeueException("Picture size too large : " + picture);
		}

		LOG.info("On image : {}", picture);
		channel.basicAck(tag, false);
	}

}
