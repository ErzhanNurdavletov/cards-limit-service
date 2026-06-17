package kg.bakaibank.cardslimitservice.payload.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CardLimitRequest(

    @NotNull(message = "newAmount can't be empty or null")
    @Min(value = 0, message = "newAmount can't be less than 0")
    @Max(value = 1_000_000, message = "newAmount can't be more than 1 000 000")
    BigDecimal newAmount,

    @NotNull(message = "newCount can't be empty or null")
    @Min(value = 1, message = "newCount can't be less than 1")
    @Max(value = 200_000, message = "newCount can't be more than 200 000")
    Integer newCount
) {
}
