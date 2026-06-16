package kg.bakaibank.cardslimitservice.exception;

import org.springframework.http.HttpStatus;

public class NewAmountCountMoreThanMaxLimitException extends ApplicationException {
    public NewAmountCountMoreThanMaxLimitException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
