package org.example;

import com.rabbitmq.client.AMQP;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.vertx.core.buffer.Buffer;
import io.vertx.rabbitmq.RabbitMQClient;
import io.vertx.rabbitmq.RabbitMQPublisher;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.HashMap;

import static org.example.Constants.RELOAD_DATA_SYNC_EMUTATION_KEY;
import static org.example.Constants.RELOAD_EMUTATION_DATA_TOPIC;

@ApplicationScoped
public class ApplicationLifecycle {
    @Inject
    Logger log;
    @Inject
    RabbitMQClient client;
    @Inject
    RabbitMQPublisher publisher;

    void onStart(@Observes StartupEvent event) {
        log.info("Application started");

        //basic example
//        basicMsgPublish();

        //Reliable Message Publishing
        reliableMsgPublish();
    }

    void onStop(@Observes ShutdownEvent event) {
        client.stop()
                .onSuccess(success -> log.info("RabbitMQ successfully stopped!"))
                .onFailure(ex -> log.info("Failed to stop RabbitMQ " + ex.getMessage()));
        log.info("Application Stopped");
    }

    private static Buffer getBuffer(String string) {
        return Buffer.buffer(string, "UTF-8");
    }

    private void basicMsgPublish() {
        client.basicPublish(RELOAD_EMUTATION_DATA_TOPIC
                , RELOAD_DATA_SYNC_EMUTATION_KEY
                , getBuffer("Hello RabbitMQ, from Vert.x !"));
    }

    private void reliableMsgPublish() {
        var messages = new HashMap<String, String>();
        messages.put("m-1", "Message 1");
        messages.put("m-2", "Message 2");
        messages.put("m-3", "Message 3");
        messages.put("m-4", "Message 4");
        messages.put("m-5", "Message 5");
        messages.forEach((k, v) -> {
            com.rabbitmq.client.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .messageId(k)
                    .build();
            publisher.publish(RELOAD_EMUTATION_DATA_TOPIC, RELOAD_DATA_SYNC_EMUTATION_KEY, properties, getBuffer(v));
        });

        publisher.getConfirmationStream().handler(conf -> {
            if (conf.isSucceeded()) {
                messages.remove(conf.getMessageId());
            }
        });
    }
}
