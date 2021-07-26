package com.example;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import io.quarkiverse.rabbitmqclient.RabbitMQClient;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

import static com.example.Constants.*;

@ApplicationScoped
public class ApplicationLifecycle {
    @Inject
    Logger log;

    @Inject
    RabbitMQClient rabbitMQClient;
    private Channel channel;

    void onStart(@Observes StartupEvent event) {
        log.info("Application started");

        setupQueues();
        sendMessage();
    }

    private void setupQueues() {
        var topicDurable = true;

        var queueDurable = true;
        var queueExclusive = false;
        var queueAutoDelete = false;

        try {
            // create a connection
            Connection connection = rabbitMQClient.connect();
            // create a channel
            channel = connection.createChannel();
            // declare exchanges and queues
            channel.exchangeDeclare(RELOAD_EMUTATION_DATA_TOPIC, BuiltinExchangeType.TOPIC, topicDurable);

            channel.queueDeclare(RELOAD_EMUTATION_QUEUE, queueDurable, queueExclusive, queueAutoDelete, null);
            channel.queueBind(RELOAD_EMUTATION_QUEUE, RELOAD_EMUTATION_DATA_TOPIC, RELOAD_DATA_SYNC_EMUTATION_KEY);

            channel.queueDeclare(RELOAD_DATA_DEBUG_QUEUE, queueDurable, queueExclusive, queueAutoDelete, null);
            channel.queueBind(RELOAD_DATA_DEBUG_QUEUE, RELOAD_EMUTATION_DATA_TOPIC, RELOAD_DATA_SYNC_KEY);

            channel.queueDeclare(RELOAD_MUKTAPATH_QUEUE, queueDurable, queueExclusive, queueAutoDelete, null);
            channel.queueBind(RELOAD_MUKTAPATH_QUEUE, RELOAD_EMUTATION_DATA_TOPIC, RELOAD_DATA_SYNC_MUKTAPATH_KEY);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void sendMessage() {
        try {
            // send a message to the exchange
            var message = "Message from Quarkus app";
            channel.basicPublish(RELOAD_EMUTATION_DATA_TOPIC, RELOAD_DATA_SYNC_EMUTATION_KEY, null, message.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
