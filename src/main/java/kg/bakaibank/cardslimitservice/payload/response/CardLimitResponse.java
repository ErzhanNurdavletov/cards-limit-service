package kg.bakaibank.cardslimitservice.payload.response;

import java.math.BigDecimal;
import java.util.UUID;

public record CardLimitResponse(
    UUID limitId,
    String limitName,
    BigDecimal currentAmount,
    Integer currentCount
) {
}
