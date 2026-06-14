package kg.bakaibank.cardslimitservice.payload.response;

import kg.bakaibank.cardslimitservice.entity.enums.CardStatus;
import kg.bakaibank.cardslimitservice.entity.enums.CardType;
import java.time.OffsetDateTime;
import java.util.UUID;

public record CardResponse(
    UUID id,
    UUID clientId,
    String maskedPan,
    CardType type,
    CardStatus status,
    OffsetDateTime openedAt,
    OffsetDateTime deletedAt
) {
}
