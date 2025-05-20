package com.serpies.talk2me.security;

import com.serpies.talk2me.model.ErrorResponseDto;
import com.serpies.talk2me.exceptions.EmailAlreadyExistsException;
import com.serpies.talk2me.exceptions.IncorrectPasswordOfUserException;
import com.serpies.talk2me.exceptions.TimeOutLoginException;
import com.serpies.talk2me.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String ERROR_PATH = "/private/errors";

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

    @MessageExceptionHandler(IllegalArgumentException.class)
    @SendToUser(ERROR_PATH)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handle(IllegalArgumentException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto("MALFORMED_REQUEST", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @MessageExceptionHandler(Exception.class)
    @SendToUser(ERROR_PATH)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handle(Exception ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto("INTERNAL_SERVER_ERROR", "Something went wrong");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
