package kg.bakaibank.cardslimitservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "limits_histories")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LimitsHistory {

    @Id
    @Column(name = "limit_history_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "card_id", nullable = false)
    private UUID cardId;

    @Column(name = "limit_id", nullable = false)
    private UUID limitId;

    @Column(name = "old_amount", precision = 15, scale = 2)
    private BigDecimal oldAmount;

    @Column(name = "new_amount", precision = 15, scale = 2, nullable = false)
    private BigDecimal newAmount;

    @Column(name = "old_count")
    private Integer oldCount;

    @Column(name = "new_count", nullable = false)
    private Integer newCount;

    @Column(name = "changed_at", nullable = false)
    private OffsetDateTime changedAt;
}
