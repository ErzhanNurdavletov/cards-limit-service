package kg.bakaibank.cardslimitservice.outbox.service;

import kg.bakaibank.cardslimitservice.outbox.event.OutboxEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxEventListener {

    private final OutboxService outboxService;

    @KafkaListener(topics = "${kafka.topic.payments}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenOutbox(OutboxEvent event) {
        if (outboxService.existsById(event.id())) {
            log.info("outbox with id: {} already listened", event.id());
        } else {
            log.info("listened outbox: {}", event);
            outboxService.save(event);
        }
    }
}
