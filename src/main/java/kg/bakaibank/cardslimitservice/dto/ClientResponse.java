package kg.bakaibank.cardslimitservice.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ClientResponse(
    UUID id,
    String firstname,
    String lastname,
    String patronymic,
    String type,
    OffsetDateTime createdAt,
    String phoneNumber
) {
}
