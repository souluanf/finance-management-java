package dev.luanfernandes.messaging;

import dev.luanfernandes.domain.event.TransactionCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.transaction-service.exchange}")
    private String transactionExchange;

    @Value("${spring.rabbitmq.transaction-service.created-routing-key}")
    private String transactionCreatedRoutingKey;

    public void publishTransactionCreatedEvent(TransactionCreatedEvent event) {
        rabbitTemplate.convertAndSend(transactionExchange, transactionCreatedRoutingKey, event);
    }
}
