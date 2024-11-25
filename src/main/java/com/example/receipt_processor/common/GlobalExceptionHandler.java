package com.example.receipt_processor.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final GlobalLogger logger;

    public GlobalExceptionHandler(GlobalLogger logger) {
        this.logger = logger;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception ex) {
        logger.error("Unhandled exception occurred", ex);

        Map<String, String> response = new HashMap<>();
        response.put("error", "An unexpected error occurred.");
        response.put("details", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
