package com.example.mercanteinfiera.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handle(ApiException ex) {
        return ResponseEntity.status(ex.getStatus()).body(
                Map.of("error", ex.getMessage())
        );
    }
}
