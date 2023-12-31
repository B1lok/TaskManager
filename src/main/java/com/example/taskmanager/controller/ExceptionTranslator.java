package com.example.taskmanager.controller;


import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.taskmanager.dto.ExceptionResponse;
import com.example.taskmanager.exception.ForbiddenAccessException;
import com.example.taskmanager.exception.InvalidPasswordException;
import com.example.taskmanager.exception.UserAlreadyExistException;
import com.example.taskmanager.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionTranslator {



    @ExceptionHandler({InvalidPasswordException.class, UserAlreadyExistException.class, DateTimeParseException.class})
    public ResponseEntity<ExceptionResponse> handleBadRequest(RuntimeException runtimeException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(runtimeException.getMessage()));
    }

    @ExceptionHandler({JWTVerificationException.class, ForbiddenAccessException.class})
    public ResponseEntity<ExceptionResponse> handleForbiddenRequest(RuntimeException runtimeException){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ExceptionResponse(runtimeException.getMessage()));
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFound(RuntimeException runtimeException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(runtimeException.getMessage()));
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleNotValidData(MethodArgumentNotValidException exception){
        Map<String, List<String>> errors = exception.getFieldErrors().stream()
                .filter(ex -> ex.getDefaultMessage()!= null)
                .collect(Collectors.groupingBy(FieldError::getField, Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())));
        return ResponseEntity.badRequest().body(errors);

    }


}
