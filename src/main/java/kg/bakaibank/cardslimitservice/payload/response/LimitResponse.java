package kg.bakaibank.cardslimitservice.payload.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record LimitResponse(
    UUID id,
    String name,
    BigDecimal maxAmount,
    Integer maxCount,
    BigDecimal defaultAmount,
    Integer defaultCount,
    OffsetDateTime deletedAt
) {
}
