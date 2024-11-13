package com.example.orderservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class RabbitMQConfig {
    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;
    @Value("${rabbitmq.queue.noti.order.name}")
    private String queueName;
    @Value("${rabbitmq.binding.noti.order.routing.key}")
    private String routingKey;
    @Value("${rabbitmq.dlx.exchange.noti.order.name}")
    private String dlxExchangeName;
    @Value("${rabbitmq.dlq.noti.order.name}")
    private String dlqExchangeName;
    @Value("${spring.rabbitmq.host}")
    private String address;

    @Bean
    public Queue notiOrderQueue() {
        return QueueBuilder
                .durable(queueName)
                .deadLetterRoutingKey(dlqExchangeName)
                .deadLetterExchange(dlxExchangeName)
                .ttl(6000)
                .build();
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Binding bindingNotiOrder() {
        return BindingBuilder
                .bind(notiOrderQueue())
                .to(exchange())
                .with(routingKey);
    }


    @Bean
    public DirectExchange dlxExchangeNotiOrder() {
        return new DirectExchange(dlxExchangeName);
    }

    @Bean
    public Queue deadLetterQueueNotiOrder() {
        return new Queue(dlqExchangeName);
    }

    @Bean
    public Binding dlqBindingNotiOrder() {
        return BindingBuilder.bind(deadLetterQueueNotiOrder())
                .to(dlxExchangeNotiOrder())
                .with(dlqExchangeName);
    }


    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setAddresses(address);
        factory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        factory.setPublisherReturns(true); // Enable publisher returns
        return factory;
    }


    // message converter
    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    // configure RabbitTemplate
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        // Enable publisher confirms
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                System.out.println("Message sent successfully: " + correlationData);
            } else {
                System.err.println("Message failed to send: " + cause);
            }
        });

        // Enable publisher returns
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnsCallback(returned -> {
            System.err.println("Message returned: " + returned.getMessage() + " - " + returned.getReplyText());
        });
        rabbitTemplate.setRetryTemplate(retryTemplate());
        return rabbitTemplate;
    }

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        // Configure retry policy
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(3); // Retry 3 times
        retryTemplate.setRetryPolicy(retryPolicy);

        // Configure backoff policy
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(500);
        backOffPolicy.setMultiplier(2.0);
        backOffPolicy.setMaxInterval(5000);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        return retryTemplate;
    }
}
