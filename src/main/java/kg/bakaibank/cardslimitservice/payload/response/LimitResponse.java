package kg.bakaibank.cardslimitservice.payload.response;

import kg.bakaibank.cardslimitservice.entity.CardCustomLimit;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

public record LimitResponse(
    UUID id,
    Set<CardCustomLimit> cardsCustomLimits,
    String name,
    BigDecimal maxAmount,
    Integer maxCount,
    BigDecimal defaultAmount,
    Integer defaultCount,
    OffsetDateTime deletedAt
) {
}
