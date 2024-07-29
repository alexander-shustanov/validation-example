package com.shustanov.customvalidator;

import jakarta.validation.ConstraintViolationException;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.method.ParameterErrors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Map<String, String>> handleValidationExceptions(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach((error) -> {
            String fieldName = error.getPropertyPath().toString();
            String errorMessage = error.getMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(
        HandlerMethodValidationException ex,
        HttpHeaders headers,
        HttpStatusCode status,
        WebRequest request
    ) {
        Map<String, String> errors = new HashMap<>();
        for (final var validation : ex.getAllValidationResults()) {
            if (validation instanceof ParameterErrors parameterErrors) {
                parameterErrors.getFieldErrors().forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
            } else {
                final String parameterName = validation.getMethodParameter().getParameterName();
                validation
                    .getResolvableErrors()
                    .forEach(
                        error -> errors.put(parameterName, error.getDefaultMessage()));
            }
        }
        ErrorDetails errorDetails = ErrorDetails
            .builder()
            .message("Validation failed")
            .details(errors)
            .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }


    @Data
    public static class ErrorDetails {

        private String message;

        private Map<?, ?> details;

        @Builder
        public ErrorDetails(String message, Map<String, String> details) {
            this.message = message;
            this.details = details;
        }
    }
}
