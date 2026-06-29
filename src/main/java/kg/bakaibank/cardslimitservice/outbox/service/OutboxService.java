package kg.bakaibank.cardslimitservice.outbox.service;

import kg.bakaibank.cardslimitservice.entity.Outbox;
import kg.bakaibank.cardslimitservice.outbox.repository.OutboxRepository;
import kg.bakaibank.cardslimitservice.outbox.event.OutboxEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OutboxService {
    private final OutboxRepository outboxRepository;

    @Transactional
    public void save(OutboxEvent outboxEvent) {
        Outbox outbox = Outbox.builder()
            .id(outboxEvent.id())
            .paymentId(outboxEvent.paymentId())
            .paymentStatus(outboxEvent.paymentStatus())
            .amount(outboxEvent.amount())
            .paymentDeclineReason(outboxEvent.paymentDeclineReason())
            .createdAt(outboxEvent.createdAt())
            .publishedAt(outboxEvent.publishedAt())
            .build();

        outboxRepository.save(outbox);
    }

    public boolean existsById(UUID id) {
        return outboxRepository.existsById(id);
    }
}
