package com.course.rabbitmq.two.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.course.rabbitmq.two.entity.DummyMessage;

@Service
public class AnotherDummyConsumer {

	private static final Logger LOG = LoggerFactory.getLogger(AnotherDummyConsumer.class);

	@RabbitListener(bindings = @QueueBinding(value = @Queue(name = "q.auto-dummy", durable = "true"), exchange = @Exchange(name = "x.auto-dummy", type = ExchangeTypes.DIRECT, durable = "true"), key = "routing-key", ignoreDeclarationExceptions = "true"))
	public void listenDummy(DummyMessage message) {
		LOG.info("Message is {}", message);
	}

}
