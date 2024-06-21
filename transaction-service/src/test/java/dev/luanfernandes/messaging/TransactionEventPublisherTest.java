package dev.luanfernandes.messaging;

import static org.mockito.Mockito.verify;

import dev.luanfernandes.domain.event.TransactionCreatedEvent;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class TransactionEventPublisherTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private TransactionEventPublisher transactionEventPublisher;

    @Test
    void shouldPublishTransactionCreatedEvent() {
        ReflectionTestUtils.setField(transactionEventPublisher, "transactionExchange", "transaction-exchange");
        ReflectionTestUtils.setField(transactionEventPublisher, "transactionCreatedRoutingKey", "transaction-created");
        TransactionCreatedEvent event = new TransactionCreatedEvent(1L, 123L, 456L, BigDecimal.TEN);
        transactionEventPublisher.publishTransactionCreatedEvent(event);
        verify(rabbitTemplate).convertAndSend("transaction-exchange", "transaction-created", event);
    }
}
