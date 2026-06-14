package kg.bakaibank.cardslimitservice.payload.request;

import kg.bakaibank.cardslimitservice.entity.enums.CardStatus;
import kg.bakaibank.cardslimitservice.entity.enums.CardType;

import java.math.BigDecimal;
import java.util.UUID;

public record CardCreateRequest(
    UUID clientId,
    String maskedPan,
    CardType type,
    CardStatus status
) {
}
