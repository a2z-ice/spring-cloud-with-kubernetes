package com.ba.cms.admin.gateway.amqp;

import com.ba.cms.admin.filter.ContextHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class GatewayRabbitConfig {

    public static final String SSLWIRELESS_SERVICE_LIST_SYNC_KEY="sslwireless.service.list.syn.key";
    public static final String SSLWIRELESS_SERVICE_LIST_SYN_QUEUE="sslwireless.service.list.syn.q";
    public static final String GATEWAY_DATA_SYNC_EXCHANGE="gateway.data.sync.ex";
    public static final String GATEWAY_SSLWIRLESS_START_SERVICE_LIST_DATA_SYN_QUEUE ="gateway.sslwireless.start.service.list.data.syn.q";
    public static final String GATEWAY_SSLWIRLESS_START_SERVICE_LIST_DATA_SYN_DEADLETTER_QUEUE ="gateway.sslwireless.start.service.list.data.syn.deadletter.q";
    public static final String SSLWIRELESS_START_SERVICE_LIST_DATA_SYNC_KEY ="sslwireless.start.service.list.data.sync.key";
    public static final String RETRY_ON_ERROR_SINGLECHANNEL_SINGLEMESSAGE_CONTAINER_FACTORY = "retryOnErrorSingleChannelSingleMessageContainerFactory";

    @Bean
    Queue sslwirelessServiceListSyncQueue() {
        return new Queue(SSLWIRELESS_SERVICE_LIST_SYN_QUEUE, true);
    }

//    @Bean
//    Queue gatewayStartDataSyncQueue() {
//
//        return QueueBuilder.durable(GATEWAY_SSLWIRLESS_START_SERVICE_LIST_DATA_SYN_QUEUE)
//                .withArgument("x-message-ttl", 10*1000) // 10 minutes in milliseconds
//                .build();
//    }
    @Bean
    Queue gatewayStartDataSyncQueue() {
        return QueueBuilder.durable(GATEWAY_SSLWIRLESS_START_SERVICE_LIST_DATA_SYN_QUEUE)
                .withArgument("x-message-ttl", 1*60*1000)
                .withArgument("x-dead-letter-exchange", "gateway.sslwireless.start.service.list.data.syn.deadletter.ex")
                .withArgument("x-dead-letter-routing-key", "gateway.sslwireless.start.service.list.data.syn.deadletter.q")
                .build();
    }

    @Bean
    Queue gatewayStartDataSyncDeadLetterQueue() {
        return QueueBuilder.durable("gateway.sslwireless.start.service.list.data.syn.deadletter.q")
                .build();
    }
    @Bean
    TopicExchange gatewayStartDataSyncDeadLetterExchange() {
        return new TopicExchange("gateway.sslwireless.start.service.list.data.syn.deadletter.ex");
    }
    @Bean
    Binding gatewayStartDataSyncDeadLetterBinding() {
        return BindingBuilder.bind(gatewayStartDataSyncDeadLetterQueue())
                .to(gatewayStartDataSyncDeadLetterExchange())
                .with("#");
    }


    @Bean
    Binding gatewayDataSyncSslWirelessBinding(Queue sslwirelessServiceListSyncQueue, TopicExchange gatewayDataSyncExchange) {
        return BindingBuilder.bind(sslwirelessServiceListSyncQueue).to(gatewayDataSyncExchange).with(SSLWIRELESS_SERVICE_LIST_SYNC_KEY);
    }
    @Bean
    Binding gatewayStartDataSync(Queue gatewayStartDataSyncQueue, TopicExchange gatewayDataSyncExchange) {
        return BindingBuilder.bind(gatewayStartDataSyncQueue).to(gatewayDataSyncExchange).with(SSLWIRELESS_START_SERVICE_LIST_DATA_SYNC_KEY);
    }
    @Bean
    TopicExchange gatewayDataSyncExchange() {
        return new TopicExchange(GATEWAY_DATA_SYNC_EXCHANGE);
    }

    @Bean
    public RabbitListenerContainerFactory<SimpleMessageListenerContainer> retryOnErrorSingleChannelSingleMessageContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            ConnectionFactory connectionFactory,
            UserContextMethodInterceptorAdapter uerContextMethodInterceptorAdapter) {

        var factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setChannelTransacted(true);
        factory.setAdviceChain(new MethodInterceptor[] {interceptor(), uerContextMethodInterceptorAdapter });
        factory.setConcurrentConsumers(1);
        factory.setPrefetchCount(1);
//        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;
    }

    @Bean
    public UserContextMethodInterceptorAdapter uerContextMethodInterceptorAdapter(ObjectMapper objectMapper, ContextHolder contextHolder){
        return new UserContextMethodInterceptorAdapter(objectMapper,contextHolder);
    }

    private RetryTemplate retryTemplate(){
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(5*1000L);//5 seconds
        backOffPolicy.setMultiplier(2.5);
        backOffPolicy.setMaxInterval(600*1000L); //60 seconds
        SimpleRetryPolicy policy = new SimpleRetryPolicy(3);
        //The below is not working need more investigation
//        SimpleRetryPolicy p  = new SimpleRetryPolicy(20,  Map.of(CustomException.class, true), true);

        RetryTemplate retryTemplate = RetryTemplate.builder().build();
        retryTemplate.setRetryPolicy(policy);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        retryTemplate.setThrowLastExceptionOnExhausted(true);
        return retryTemplate;
    }

    private RetryOperationsInterceptor interceptor() {
        return RetryInterceptorBuilder.stateless()
                .retryOperations(retryTemplate())
                .recoverer(new RejectAndDontRequeueRecoverer())
                .build();
    }

    @Bean
    Jackson2JsonMessageConverter converter (ObjectMapper objectMapper){
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
