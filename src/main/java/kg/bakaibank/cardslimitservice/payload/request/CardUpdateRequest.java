package kg.bakaibank.cardslimitservice.payload.request;

import kg.bakaibank.cardslimitservice.entity.CardCustomLimit;
import kg.bakaibank.cardslimitservice.entity.enums.CardStatus;
import kg.bakaibank.cardslimitservice.entity.enums.CardType;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

public record CardUpdateRequest(
    UUID id,
    UUID clientId,
    String maskedPan,
    CardType type,
    CardStatus status,
    OffsetDateTime openedAt
) {
}
