package com.example.taskmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import java.time.Instant;

public class ForbiddenAccessException extends ErrorResponseException {
    public ForbiddenAccessException(String message) {
        super(HttpStatus.FORBIDDEN,asProblemDetail(message) , null);
    }

    private static ProblemDetail asProblemDetail(String message){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, message);
        problemDetail.setTitle("Access forbidden");
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}
