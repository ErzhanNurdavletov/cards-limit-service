package kg.bakaibank.cardslimitservice.exception;

import org.springframework.http.HttpStatus;

public class IdNotMatchBodyClientIdException extends ApplicationException {
    public IdNotMatchBodyClientIdException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
