package kg.bakaibank.cardslimitservice.payload.response;

import java.math.BigDecimal;

public record CardLimitResponse(

    BigDecimal currentAmount,
    Integer currentCount
) {
}
