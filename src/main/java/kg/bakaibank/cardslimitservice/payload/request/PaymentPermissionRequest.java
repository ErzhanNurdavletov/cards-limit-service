package kg.bakaibank.cardslimitservice.payload.request;

import java.math.BigDecimal;

public record PaymentPermissionRequest(
    BigDecimal todayPaymentAmount,
    int todayPaymentCount
) {
}
