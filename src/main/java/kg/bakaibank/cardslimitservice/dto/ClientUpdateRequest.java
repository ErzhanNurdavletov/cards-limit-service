package kg.bakaibank.cardslimitservice.dto;

import java.util.UUID;

public record ClientUpdateRequest(
    UUID id,
    String firstname,
    String lastname,
    String patronymic,
    String type,
    String phoneNumber
) {
}
