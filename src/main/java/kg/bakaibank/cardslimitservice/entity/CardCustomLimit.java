package kg.bakaibank.cardslimitservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "cards_limits")
@Data
@NoArgsConstructor
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
    private BigDecimal currentAmount = new BigDecimal("200000.00");

    @Column(name = "current_count", nullable = false)
    private Integer currentCount = 2000;
}
