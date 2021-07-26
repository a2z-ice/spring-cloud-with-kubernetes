package org.example;

import io.vertx.core.Vertx;
import io.vertx.rabbitmq.RabbitMQClient;
import io.vertx.rabbitmq.RabbitMQOptions;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Produces;

import static org.example.Constants.*;

public class RabbitmqClientProducer {
    @ConfigProperty(name = "vertx-rabbitmq-virtual-host")
    String virtualHost;
    @ConfigProperty(name = "vertx-rabbitmq-username")
    String username;
    @ConfigProperty(name = "vertx-rabbitmq-password")
    String password;
    @ConfigProperty(name = "vertx-rabbitmq-hostname")
    String host;
    @ConfigProperty(name = "vertx-rabbitmq-port")
    int port;

    @Inject
    Vertx vertx;

    @Inject
    Logger log;

    @Produces
    @ApplicationScoped
    public RabbitMQClient produceRabbitmqClient() {
        RabbitMQOptions config = new RabbitMQOptions();
        setRabbitmqConfig(config);

        RabbitMQClient client = RabbitMQClient.create(vertx, config);
        setOnConnectionEstablishedCallback(client);

        // Connect
        client.start()
                .onSuccess(event -> log.info("RabbitMQ successfully connected!"))
                .onFailure(ex -> log.info("Fail to connect to RabbitMQ " + ex.getMessage()));

        return client;
    }

    private void setOnConnectionEstablishedCallback(RabbitMQClient client) {
        var topicDurable = true;
        var topicAutoDelete = false;

        var queueDurable = true;
        var queueExclusive = false;
        var queueAutoDelete = false;

        client.addConnectionEstablishedCallback(promise ->
                client.exchangeDeclare(RELOAD_EMUTATION_DATA_TOPIC, "topic", topicDurable, topicAutoDelete)
                        .compose(v -> client.queueDeclare(RELOAD_EMUTATION_QUEUE, queueDurable, queueExclusive, queueAutoDelete))
                        .compose(declareOk ->
                                client.queueBind(declareOk.getQueue(), RELOAD_EMUTATION_DATA_TOPIC, RELOAD_DATA_SYNC_EMUTATION_KEY))
                        .compose(v -> client.queueDeclare(RELOAD_DATA_DEBUG_QUEUE, queueDurable, queueExclusive, queueAutoDelete))
                        .compose(declareOk ->
                                client.queueBind(declareOk.getQueue(), RELOAD_EMUTATION_DATA_TOPIC, RELOAD_DATA_SYNC_KEY))
                        .compose(v -> client.queueDeclare(RELOAD_MUKTAPATH_QUEUE, queueDurable, queueExclusive, queueAutoDelete))
                        .compose(declareOk ->
                                client.queueBind(declareOk.getQueue(), RELOAD_EMUTATION_DATA_TOPIC, RELOAD_DATA_SYNC_MUKTAPATH_KEY))
                        .onComplete(promise));
    }

    private void setRabbitmqConfig(RabbitMQOptions config) {
        // Each parameter is optional
        // The default parameter with be used if the parameter is not set
        config.setUser(username);
        config.setPassword(password);
        config.setHost(host);
        config.setPort(port);
        config.setVirtualHost(virtualHost);
        config.setConnectionTimeout(6000); // in milliseconds
        config.setRequestedHeartbeat(60); // in seconds
        config.setHandshakeTimeout(6000); // in milliseconds
        config.setRequestedChannelMax(5);
        config.setNetworkRecoveryInterval(500); // in milliseconds
        config.setAutomaticRecoveryEnabled(false);
        config.setReconnectAttempts(100);
        config.setReconnectInterval(10000);
    }
}
