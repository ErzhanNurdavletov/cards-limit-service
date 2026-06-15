package kg.bakaibank.cardslimitservice.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kg.bakaibank.cardslimitservice.entity.enums.CardStatus;
import kg.bakaibank.cardslimitservice.entity.enums.CardType;

import java.util.UUID;

public record CardCreateRequest(

    @NotNull(message = "clientId can't be null")
    UUID clientId,

    @NotBlank(message = "pan can't be null or empty or blank")
    @Size(max = 19, message = "pan length can't be more than 19")
    String maskedPan,

    @NotNull(message = "type can be only DEBIT or CREDIT")
    CardType type,

    @NotNull(message = "status can be only ACTIVE or BLOCKED")
    CardStatus status
) {
}