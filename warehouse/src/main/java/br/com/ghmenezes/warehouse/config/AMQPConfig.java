package br.com.ghmenezes.warehouse.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AMQPConfig {

    @Value("${spring.rabbitmq.routing-key.dead-letter}")
    private String DEAD_LETTER_ROUTING_KEY;

    @Bean
    Jackson2JsonMessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory,
                                  final Jackson2JsonMessageConverter converter){
        var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter);
        return rabbitTemplate;

    }

    @Bean
    Queue productStatusChangeQueue(@Value("${spring.rabbitmq.queue.product-change-availability}") final String name) {
        return new Queue(name, true);
    }

    @Bean
    Queue productCreatedQueue(@Value("${spring.rabbitmq.queue.product-created}") final String name) {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", "");
        args.put("x-dead-letter-routing-key", DEAD_LETTER_ROUTING_KEY);
        return new Queue(name, true, false, false, args);
    }

    @Bean
    Queue deadLetterQueue(@Value("${spring.rabbitmq.queue.dead-letter}") final String name) {
        return new Queue(name, true);
    }

    @Bean
    TopicExchange productExchange(@Value("${spring.rabbitmq.exchange.product-events}") final String name) {
        return new TopicExchange(name);
    }

    @Bean
    Binding productStatusChangeBinding(Queue productStatusChangeQueue, TopicExchange productExchange,
                                @Value("${spring.rabbitmq.routing-key.product-change-availability}") final String routingKey) {
        return BindingBuilder.bind(productStatusChangeQueue).to(productExchange).with(routingKey);
    }

    @Bean
    Binding productCreatedBinding(Queue productCreatedQueue, TopicExchange productExchange,
                           @Value("${spring.rabbitmq.routing-key.product-created}") final String routingKey) {
        return BindingBuilder.bind(productCreatedQueue).to(productExchange).with(routingKey);
    }

    @Bean
    Binding deadLetterBinding(Queue deadLetterQueue, TopicExchange deadLetterExchange,
                                  @Value("${spring.rabbitmq.routing-key.dead-letter}") final String routingKey) {
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with(routingKey);
    }

}
