package kg.bakaibank.cardslimitservice.payload.request;

import kg.bakaibank.cardslimitservice.entity.enums.ClientType;

import java.util.UUID;

public record ClientUpdateRequest(
    UUID id,
    String firstname,
    String lastname,
    String patronymic,
    ClientType type,
    String phoneNumber
) {
}
