package com.valdirsantos714.backend.infrastructure;

import com.auth0.jwt.exceptions.JWTCreationException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String AUTHENTICATION_ERROR_MESSAGE = "Authentication error: ";
    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "An internal server error occurred. Please try again later.";

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleBadRequestException(MethodArgumentNotValidException exception) {
        var fieldErrors = exception.getFieldErrors();
        return ResponseEntity.badRequest().body(fieldErrors.stream().map(ValidationErrorDetails::new).toList());
    }

    @ExceptionHandler(JWTCreationException.class)
    public ResponseEntity handleUnauthorizedException(JWTCreationException exception) {
        return ResponseEntity.status(401).body(new ErrorDto(AUTHENTICATION_ERROR_MESSAGE + exception.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity handleInternalServerError(RuntimeException exception) {
        return ResponseEntity.status(500).body(new ErrorDto(INTERNAL_SERVER_ERROR_MESSAGE));
    }


    private record ErrorDto(String message) {
    }

    private record ValidationErrorDetails(String field, String message) {
        public ValidationErrorDetails(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
