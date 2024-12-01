package com.example.moviesapi.exceptions;

import jakarta.validation.UnexpectedTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class Handler {

    private static final String TIMESTAMP = "timestamp";
    private static final String MESSAGE = "message";
    private static final String STATUS = "status";
    private static final String ERROR = "error";
    private static final String PATH = "path";
    private static final String BAD_REQUEST = "Bad Request";
    private static final String NOT_FOUND = "Not Found";
    private static final String INVALID_FORMAT_MESSAGE = "Invalid date, duration or year format. Please use 'yyyy-MM-dd', 'HH:mm:ss' and 'yyyy' respectively. If this is not the case please check other fields.";

    @ExceptionHandler(NotFound.class)
    public ResponseEntity<Object> handleCustomException(NotFound ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, NOT_FOUND);
    }

    @ExceptionHandler(InvalidData.class)
    public ResponseEntity<Object> handleCustomException(InvalidData ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
        return buildErrorResponse(INVALID_FORMAT_MESSAGE, HttpStatus.BAD_REQUEST, BAD_REQUEST, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleConstraintViolationException(MethodArgumentNotValidException ex, WebRequest request) {
        String message = ex.getBindingResult().getFieldError().getField() + " " + ex.getBindingResult().getFieldError().getDefaultMessage();
        return buildErrorResponse(message, HttpStatus.BAD_REQUEST, BAD_REQUEST, request);
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(UnexpectedTypeException ex, WebRequest request) {
        return buildErrorResponse(INVALID_FORMAT_MESSAGE, HttpStatus.BAD_REQUEST, BAD_REQUEST, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(IllegalArgumentException ex, WebRequest request) {
        String message = "Invalid parameter error. " + ex.getMessage();
        return buildErrorResponse(message, HttpStatus.BAD_REQUEST, BAD_REQUEST, request);
    }

    private ResponseEntity<Object> buildErrorResponse(String message, HttpStatus status, String error) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put(TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        errorDetails.put(MESSAGE, message);
        errorDetails.put(STATUS, String.valueOf(status.value()));
        errorDetails.put(ERROR, error);
        return new ResponseEntity<>(errorDetails, status);
    }

    private ResponseEntity<Object> buildErrorResponse(String message, HttpStatus status, String error, WebRequest request) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put(TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        errorDetails.put(MESSAGE, message);
        errorDetails.put(STATUS, String.valueOf(status.value()));
        errorDetails.put(PATH, request.getDescription(false));
        errorDetails.put(ERROR, error);
        return new ResponseEntity<>(errorDetails, status);
    }
}
