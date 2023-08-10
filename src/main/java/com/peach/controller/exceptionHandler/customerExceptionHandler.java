package com.peach.controller.exceptionHandler;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class customerExceptionHandler {

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiError> handleSQLException(DataAccessException e) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Database Error: " + e.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Boolean> handleEmptyResult(EmptyResultDataAccessException e) {
        return new ResponseEntity<>(false, HttpStatus.OK);
    }

    @ExceptionHandler(CannotGetJdbcConnectionException.class)
    public ResponseEntity<ApiError> handleJdbcConnectionException(CannotGetJdbcConnectionException e) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "JDBC connection error: " + e.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleExceptions(Exception e) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Runtime exception: " + e.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}