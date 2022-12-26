package com.course.rabbitmq.two.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Binding.DestinationType;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqSchemaConfig {

//	@Bean
//	public FanoutExchange fanoutExchange() {
//		return new FanoutExchange("x.another-dummy", true, false);
//	}
//
//	@Bean
//	public Queue queueAnotherDummy() {
//		return new Queue("q.another-dummy");
//	}
//
//	@Bean
//	public Binding bindingAnotherDummy() {
//		return new Binding("q.another-dummy", DestinationType.QUEUE, "x.another-dummy", "", null);
//		return BindingBuilder.bind(queueAnotherDummy()).to(fanoutExchange());
//	}

	@Bean
	public Declarables rabbitmqSchema() {
		return new Declarables(new FanoutExchange("x.another-dummy", true, false), new Queue("q.another-dummy"),
				new Binding("q.another-dummy", DestinationType.QUEUE, "x.another-dummy", "", null));
	}

}
