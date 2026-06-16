package kg.bakaibank.cardslimitservice.payload.request;

import jakarta.validation.constraints.*;
import kg.bakaibank.cardslimitservice.entity.enums.CardStatus;
import kg.bakaibank.cardslimitservice.entity.enums.CardType;

import java.time.OffsetDateTime;

public record CardUpdateRequest(

    @NotBlank(message = "pan can't be null or empty or blank")
    @Size(max = 19, message = "pan length can't be more than 19")
    String maskedPan,

    @NotNull(message = "type can be only DEBIT or CREDIT")
    CardType type,

    @NotNull(message = "status can be only ACTIVE or BLOCKED")
    CardStatus status,

    @FutureOrPresent(message = "closedAt date can't be in Past")
    @NotNull(message = "closedAt can't be null")
    OffsetDateTime closedAt,

    @NotBlank(message = "cardIssueTypeName can't be null or empty or blank")
    @Size(max = 100, message = "cardIssueTypeName length can't be more than 100")
    String cardIssueTypeName
) {
}