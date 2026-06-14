package kg.bakaibank.cardslimitservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cards_limits")
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class CardCustomLimit {

    @EmbeddedId
    private CardCustomLimitCompositeKey id;

    @ManyToOne
    @MapsId("cardId")
    @JoinColumn(name = "card_id")
    private Card card;

    @ManyToOne
    @MapsId("limitId")
    @JoinColumn(name = "limit_id")
    private Limit limit;

    @Column(name = "current_amount", precision = 15, scale = 2, nullable = false)
    private BigDecimal currentAmount;

    @Column(name = "current_count", nullable = false)
    private Integer currentCount;
}
