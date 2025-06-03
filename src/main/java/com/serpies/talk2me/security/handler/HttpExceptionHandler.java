package com.serpies.talk2me.security.handler;

import com.serpies.talk2me.exceptions.*;
import com.serpies.talk2me.model.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HttpExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handle(UserNotFoundException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto("USER_NOT_FOUND", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handle(EmailAlreadyExistsException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto("EMAIL_ALREADY_EXISTS", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectPasswordOfUserException.class)
    public ResponseEntity<ErrorResponseDto> handle(IncorrectPasswordOfUserException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto("INCORRECT_PASSWORD_OF_USER", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TimeOutLoginException.class)
    public ResponseEntity<ErrorResponseDto> handle(TimeOutLoginException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto("TIME_OUT_LOGIN", ex.getMessage(), ex.getTimeOut());
        return new ResponseEntity<>(errorResponse, HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handle(IllegalArgumentException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto("MALFORMED_REQUEST", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handle(FileNotFoundException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto("FILE_NOT_FOUND", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handle(Exception ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto("INTERNAL_SERVER_ERROR", "Something went wrong");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
