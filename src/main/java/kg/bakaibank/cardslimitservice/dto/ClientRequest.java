package kg.bakaibank.cardslimitservice.dto;

public record ClientRequest(
    String firstname,
    String lastname,
    String patronymic,
    String type,
    String phoneNumber
) {
}
