package kg.bakaibank.cardslimitservice.payload.response;

public record PaymentPermissionResponse(
    boolean isAllowedByAmount,
    boolean isAllowedByCount
) {
}
