package org.example;

import io.vertx.core.Vertx;
import io.vertx.rabbitmq.RabbitMQClient;
import io.vertx.rabbitmq.RabbitMQPublisher;
import io.vertx.rabbitmq.RabbitMQPublisherOptions;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Produces;

public class RabbitmqPublisherProducer {
    @Inject
    Vertx vertx;

    @Inject
    RabbitMQClient client;

    @Produces
    @ApplicationScoped
    public RabbitMQPublisher produceRabbitMQPublisher() {
        RabbitMQPublisherOptions options = new RabbitMQPublisherOptions();
//        options.setReconnectInterval() -- default: 10000
        options.setReconnectAttempts(100);
        options.setMaxInternalQueueSize(256);
        return RabbitMQPublisher.create(vertx, client, options);
    }
}
