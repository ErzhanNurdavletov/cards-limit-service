package kg.bakaibank.cardslimitservice.payload.response;

import kg.bakaibank.cardslimitservice.entity.enums.ClientType;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ClientResponse(
    UUID id,
    String firstname,
    String lastname,
    String patronymic,
    ClientType type,
    OffsetDateTime createdAt,
    OffsetDateTime deletedAt,
    String phoneNumber
) {
}
