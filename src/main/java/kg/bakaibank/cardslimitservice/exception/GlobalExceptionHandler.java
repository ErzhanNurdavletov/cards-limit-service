package kg.bakaibank.cardslimitservice.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException e,
                                                       HttpServletRequest request) {
        List<ValidationError> errors = e.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(fieldError ->
                ValidationError.builder()
                    .field(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .build()).toList();

        ErrorResponse response = ErrorResponse.builder()
            .error("Validation Failed")
            .status(HttpStatus.BAD_REQUEST.value())
            .message("Arguments didn't pass validation")
            .timestamp(OffsetDateTime.now())
            .validationErrors(errors)
            .path(request.getRequestURI())
            .build();

        log.warn("Handled not valid request");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException e) {
        ErrorResponse response = ErrorResponse.builder()
            .message("Entity not found")
            .status(HttpStatus.NOT_FOUND.value())
            .error(e.getMessage())
            .timestamp(OffsetDateTime.now())
            .build();
        log.warn("Not found entity, message: {}", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NewAmountCountMoreThanMaxLimitException.class)
    public ResponseEntity<?> handleNewAmountCountMoreThanMaxLimitException(
        NewAmountCountMoreThanMaxLimitException e) {
        ErrorResponse response = ErrorResponse.builder()
            .message("New Count or(and) amount is bigger than max allowed values in limit")
            .status(HttpStatus.BAD_REQUEST.value())
            .error(e.getMessage())
            .timestamp(OffsetDateTime.now())
            .build();
        log.warn("New Count or(and) amount is bigger than max allowed values, message: {}", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CardIsBlockedException.class)
    public ResponseEntity<?> handleCardIsBlockedException(
        CardIsBlockedException e) {
        ErrorResponse response = ErrorResponse.builder()
            .message("Not allowed to change BLOCKED card custom limit")
            .status(HttpStatus.BAD_REQUEST.value())
            .error(e.getMessage())
            .timestamp(OffsetDateTime.now())
            .build();
        log.warn("Not allowed to change BLOCKED card custom limit, message: {}", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {

        String customMessage = "Invalid format of data";
        ErrorResponse response = ErrorResponse.builder()
            .message(customMessage)
            .status(HttpStatus.BAD_REQUEST.value())
            .error(e.getMessage())
            .timestamp(OffsetDateTime.now())
            .build();

        log.warn("some http parts are not valid, message: {}", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e,
                                                            HttpServletRequest request) {
        ErrorResponse response = ErrorResponse.builder()
            .message("Illegal arguments were given")
            .status(HttpStatus.BAD_REQUEST.value())
            .error(e.getMessage())
            .timestamp(OffsetDateTime.now())
            .path(request.getRequestURI())
            .build();
        log.warn("Illegal arguments were given, message: {}", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<?> handleApplicationException(ApplicationException e,
                                                        HttpServletRequest request) {
        ErrorResponse response = ErrorResponse.builder()
            .message("Business logic exception")
            .status(HttpStatus.BAD_REQUEST.value())
            .error(e.getMessage())
            .timestamp(OffsetDateTime.now())
            .path(request.getRequestURI())
            .build();
        log.warn("Not specified app exception handled, message: {}", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUncaughtException(Exception e,
                                                     HttpServletRequest request) {
        log.error("Uncaught Exception {}", request.getRequestURI(), e);

        ErrorResponse response = ErrorResponse.builder()
            .error(e.getMessage())
            .timestamp(OffsetDateTime.now())
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .message("Internal Uncaught Server Error")
            .path(request.getRequestURI())
            .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
