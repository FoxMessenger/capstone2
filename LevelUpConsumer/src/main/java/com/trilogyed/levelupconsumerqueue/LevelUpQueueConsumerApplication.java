package com.trilogyed.levelupconsumerqueue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;



@SpringBootApplication
@EnableFeignClients
public class LevelUpQueueConsumerApplication {

	public static final String TOPIC_EXHCANGE_NAME = "level-up-exchange";
	public static final String QUEUE_NAME = "level-up-queue";
	public static final String ROUTING_KEY = "levelUp.#";

	@Bean
	Queue queue(){
		return new Queue(QUEUE_NAME, false);
	}

	@Bean
	TopicExchange exchange(){
		return  new TopicExchange(TOPIC_EXHCANGE_NAME);
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange){
		return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
	}

	@Bean
	Jackson2JsonMessageConverter jackson2JsonMessageConverter(){
		return new Jackson2JsonMessageConverter();
	}

	public static void main(String[] args) {
		SpringApplication.run(LevelUpQueueConsumerApplication.class, args);
	}

}
