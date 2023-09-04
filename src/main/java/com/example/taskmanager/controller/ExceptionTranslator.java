package com.example.taskmanager.controller;


import com.auth0.jwt.exceptions.JWTDecodeException;
import com.example.taskmanager.dto.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionTranslator {



    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequest(RuntimeException runtimeException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(runtimeException.getMessage()));
    }

    @ExceptionHandler(JWTDecodeException.class)
    public ResponseEntity<ExceptionResponse> handleForbiddenRequest(RuntimeException runtimeException){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ExceptionResponse(runtimeException.getMessage()));
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleNotValidData(MethodArgumentNotValidException exception){
        Map<String, String> errors = exception.getFieldErrors().stream()
                .filter(ex -> ex.getDefaultMessage()!= null)
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        return ResponseEntity.badRequest().body(errors);

    }


}
