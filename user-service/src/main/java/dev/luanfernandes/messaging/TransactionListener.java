package dev.luanfernandes.messaging;

import dev.luanfernandes.domain.event.TransactionCreatedEvent;
import dev.luanfernandes.domain.response.UserResponse;
import dev.luanfernandes.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionListener {

    private final UserServiceImpl userService;

    @RabbitListener(queues = "${spring.rabbitmq.transaction-service.queue}")
    public void handleTransaction(TransactionCreatedEvent event) {
        UserResponse fromUser = userService.getUserById(event.fromUserId());
        UserResponse toUser = userService.getUserById(event.toUserId());
        log.info("Received transaction: {} from {} to {}", event.id(), fromUser.name(), toUser.name());
    }
}
