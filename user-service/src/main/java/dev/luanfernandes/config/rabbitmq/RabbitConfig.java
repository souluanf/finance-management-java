package dev.luanfernandes.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${spring.rabbitmq.transaction-service.queue}")
    private String transactionQueue;

    @Value("${spring.rabbitmq.transaction-service.exchange}")
    private String transactionExchange;

    @Value("${spring.rabbitmq.transaction-service.created-routing-key}")
    private String transactionCreatedRoutingKey;

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public TopicExchange transactionExchange() {
        return new TopicExchange(transactionExchange);
    }

    @Bean
    public Queue transactionCreatedQueue() {
        return new Queue(transactionQueue, true);
    }

    @Bean
    public Binding transactionCreatedBinding(Queue transactionCreatedQueue, TopicExchange transactionExchange) {
        return BindingBuilder.bind(transactionCreatedQueue)
                .to(transactionExchange)
                .with(transactionCreatedRoutingKey);
    }
}
