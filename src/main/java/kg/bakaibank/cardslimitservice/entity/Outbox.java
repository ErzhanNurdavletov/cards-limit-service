package kg.bakaibank.cardslimitservice.entity;

import jakarta.persistence.*;
import kg.bakaibank.cardslimitservice.outbox.event.enums.PaymentDeclineReason;
import kg.bakaibank.cardslimitservice.outbox.event.enums.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "outboxes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = {"id", "paymentStatus", "amount", "createdAt"})
public class Outbox {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "payment_id")
    private UUID paymentId;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "payment_decline_reason")
    @Enumerated(EnumType.STRING)
    private PaymentDeclineReason paymentDeclineReason;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "published_at")
    private OffsetDateTime publishedAt;
}
