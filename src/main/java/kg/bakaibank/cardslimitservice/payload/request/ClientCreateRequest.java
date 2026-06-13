package kg.bakaibank.cardslimitservice.payload.request;

import kg.bakaibank.cardslimitservice.entity.enums.ClientType;

public record ClientCreateRequest(
    String firstname,
    String lastname,
    String patronymic,
    ClientType type,
    String phoneNumber
) {
}
