package kg.bakaibank.cardslimitservice.payload.request;

import java.math.BigDecimal;
import java.util.UUID;

public record LimitUpdateRequest(
    UUID id,
    String name,
    BigDecimal maxAmount,
    Integer maxCount,
    BigDecimal defaultAmount,
    Integer defaultCount
) {
}
