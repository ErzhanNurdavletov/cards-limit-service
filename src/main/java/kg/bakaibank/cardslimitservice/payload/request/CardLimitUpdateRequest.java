package kg.bakaibank.cardslimitservice.payload.request;

import java.math.BigDecimal;

public record CardLimitUpdateRequest(
    BigDecimal newAmount,
    Integer newCount
) {
}
