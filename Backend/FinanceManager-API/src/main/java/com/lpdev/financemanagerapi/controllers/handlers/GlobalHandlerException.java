package com.lpdev.financemanagerapi.controllers.handlers;

import com.lpdev.financemanagerapi.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalHandlerException {

    // Handler of General Internal error Exceptions - 500
    @ExceptionHandler(FinanceManagerException.class)
    public ResponseEntity<StandardError> handleFinanceManagerException(FinanceManagerException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500
        String name = "internal server error";
        String message = ex.getMessage();
        String path = request.getRequestURI();
        Instant now = Instant.now();

        StandardError standardError =
                StandardError.builder()
                        .status(status)
                        .name(name)
                        .message(message)
                        .path(path)
                        .timestamp(now)
                        .build();

        return ResponseEntity.status(status).body(standardError);
    }

    // Handler of security exceptions -- UNAUTHORIZED - 401
    @ExceptionHandler(FinanceManagerSecurityException.class)
    public ResponseEntity<StandardError> handleFinanceManagerSecurityException(FinanceManagerSecurityException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.UNAUTHORIZED; // 401
        String name = "Unauthorized";
        String message = ex.getMessage();
        String path = request.getRequestURI();
        Instant now = Instant.now();

        StandardError standardError =
                StandardError.builder()
                        .status(status)
                        .name(name)
                        .message(message)
                        .path(path)
                        .timestamp(now)
                        .build();

        return ResponseEntity.status(status).body(standardError);
    }

    // Handler of conflict exceptions -- CONFLICT - 409
    @ExceptionHandler(FinanceManagerConflictException.class)
    public ResponseEntity<StandardError> handleFinanceManagerConflictException(FinanceManagerConflictException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.CONFLICT; // 409
        String name = "Conflict";
        String message = ex.getMessage();
        String path = request.getRequestURI();
        Instant now = Instant.now();

        StandardError standardError =
                StandardError.builder()
                        .status(status)
                        .name(name)
                        .message(message)
                        .path(path)
                        .timestamp(now)
                        .build();

        return ResponseEntity.status(status).body(standardError);
    }
  // Handler of Not found exceptions -- NOT FOUND - 404
    @ExceptionHandler(FinanceManagerNotFoundException.class)
    public ResponseEntity<StandardError> handleFinanceManagerNotFoundException(FinanceManagerNotFoundException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND; // 404
        String name = "Not found";
        String message = ex.getMessage();
        String path = request.getRequestURI();
        Instant now = Instant.now();

        StandardError standardError =
                StandardError.builder()
                        .status(status)
                        .name(name)
                        .message(message)
                        .path(path)
                        .timestamp(now)
                        .build();

        return ResponseEntity.status(status).body(standardError);
    }

    // Handler of Bad request exceptions -- BAD REQUEST  - 400
    @ExceptionHandler(FinanceManagerBadRequestException.class)
    public ResponseEntity<StandardError> handleFinanceManagerBadRequestException(FinanceManagerNotFoundException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST; // 400
        String name = "Bad request";
        String message = ex.getMessage();
        String path = request.getRequestURI();
        Instant now = Instant.now();

        StandardError standardError =
                StandardError.builder()
                        .status(status)
                        .name(name)
                        .message(message)
                        .path(path)
                        .timestamp(now)
                        .build();

        return ResponseEntity.status(status).body(standardError);
    }

}
