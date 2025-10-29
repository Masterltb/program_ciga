package com.smokefree.program.web.error;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {
    record Err(String code, String message, Map<String,Object> extra) {}

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> nf(NotFoundException e){ return body("NOT_FOUND", e.getMessage(), HttpStatus.NOT_FOUND); }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<?> fb(ForbiddenException e){ return body("FORBIDDEN", e.getMessage(), HttpStatus.FORBIDDEN); }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?> cf(ConflictException e){ return body("CONFLICT", e.getMessage(), HttpStatus.CONFLICT); }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> ve(ValidationException e){ return body("VALIDATION_ERROR", e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY); }

    @ExceptionHandler(SubscriptionRequiredException.class)
    public ResponseEntity<?> sr(SubscriptionRequiredException e){ return body("SUBSCRIPTION_REQUIRED", e.getMessage(), HttpStatus.FORBIDDEN); }

    private ResponseEntity<Err> body(String c, String m, HttpStatus s) {
        return ResponseEntity.status(s).body(new Err(c, m, Map.of()));
    }
}
