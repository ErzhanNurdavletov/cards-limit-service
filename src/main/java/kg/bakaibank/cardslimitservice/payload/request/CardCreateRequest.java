package kg.bakaibank.cardslimitservice.payload.request;

import jakarta.validation.constraints.*;
import kg.bakaibank.cardslimitservice.entity.enums.CardStatus;
import kg.bakaibank.cardslimitservice.entity.enums.CardType;

import java.util.UUID;

public record CardCreateRequest(

    @NotNull(message = "clientId can't be null")
    UUID clientId,

    @NotBlank(message = "pan can't be null or empty or blank")
    @Pattern(regexp = "^\\d{16}$", message = "pan length must be 16")
    String maskedPan,

    @NotNull(message = "type can be only DEBIT or CREDIT")
    CardType type,

    @NotNull(message = "status can be only ACTIVE or BLOCKED")
    CardStatus status,

    @NotBlank(message = "cardIssueTypeName can't be null or empty or blank")
    @Size(max = 100, message = "cardIssueTypeName length can't be more than 100")
    String cardIssueTypeName,

    @NotNull(message = "accountId can't be null")
    UUID accountId
) {
}