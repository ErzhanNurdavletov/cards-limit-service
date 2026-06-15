package kg.bakaibank.cardslimitservice.payload.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record LimitCreateRequest(

    @NotBlank(message = "name can't be empty or null")
    @Size(min = 1, max = 50, message = "name must be unique, minimum size = 1, max size = 50(include)")
    String name,

    @NotNull(message = "maxAmount can't be empty or null")
    @Min(value = 0, message = "maxAmount can't be less than 0")
    @Max(value = 1_000_000, message = "maxAmount can't be more than 1 000 000")
    BigDecimal maxAmount,

    @NotNull(message = "maxCount can't be empty or null")
    @Min(value = 1, message = "maxCount can't be less than 1")
    @Max(value = 200_000, message = "maxCount can't be more than 200 000")
    Integer maxCount,

    @NotNull(message = "defaultAmount can't be empty or null")
    @Min(value = 0, message = "defaultAmount can't be less than 0")
    @Max(value = 1_000_000, message = "defaultAmount can't be more than 1 000 000")
    BigDecimal defaultAmount,

    @NotNull(message = "defaultCount can't be empty or null")
    @Min(value = 1, message = "defaultCount can't be less than 1")
    @Max(value = 200_000, message = "defaultCount can't be more than 200 000")
    Integer defaultCount
) {
}