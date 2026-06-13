package kg.bakaibank.cardslimitservice.payload.request;

import java.math.BigDecimal;

public record LimitCreateRequest(
    String name,
    BigDecimal maxAmount,
    Integer maxCount,
    BigDecimal defaultAmount,
    Integer defaultCount
    ) {
}
