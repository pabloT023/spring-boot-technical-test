package com.bank.orders_service.exception;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Exception when an order item doesn't exist
    @ExceptionHandler(OrderItemsNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleOrderItemsNotFoundException(OrderItemsNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error","Order items empty");
        response.put("message",ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Exception when an order doesn't exist
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleOrderNotFoundException(OrderNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error","Order not found");
        response.put("message",ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Exception when an order is canceled
    @ExceptionHandler(OrderCanceledException.class)
    public ResponseEntity<Map<String, String>> handleOrderCanceledException(OrderCanceledException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error","Order canceled");
        response.put("message",ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    // Exception when an order is paid
    @ExceptionHandler(OrderPaidException.class)
    public ResponseEntity<Map<String, String>> handleOrderPaidException(OrderPaidException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error","Order paid");
        response.put("message",ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    // Exception when a product doesn't exist
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProductNotFoundException(ProductNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error","Product not found");
        response.put("message",ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Exception when request body fails validations
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("error", "Bad request");
        response.put("details", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    //Exception when request body has invalid data types
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleJsonParseException(HttpMessageNotReadableException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Bad request");

        if (ex.getCause() instanceof MismatchedInputException) {
            response.put("message", "Invalid data type in request body.");
        } else {
            response.put("message", "Malformed JSON request.");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Exception when an internal error occurred
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Internal Server Error");
        response.put("message","An unexpected error occurred. Please contact the system administrator.");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }





}
