package com.course.rabbitmq.two.consumer;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import com.course.rabbitmq.two.entity.DummyMessage;

//@Service
public class MultiplePrefetchConsumer {

	private static final Logger LOG = LoggerFactory.getLogger(MultiplePrefetchConsumer.class);

	@RabbitListener(queues = "q.transaction", concurrency = "2")
	public void listenTransaction(DummyMessage message) throws InterruptedException {
		LOG.info("Taking transaction {}", message);
		TimeUnit.MILLISECONDS.sleep(100);
	}

	@RabbitListener(queues = "q.scheduler", concurrency = "2", containerFactory = "prefetchOneContainerFactory")
	public void listenScheduler(DummyMessage message) throws InterruptedException {
		LOG.info("Taking scheduler {}", message);
		TimeUnit.MINUTES.sleep(1);
	}

}
