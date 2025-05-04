package com.serpies.talk2me.utilities.security;

import com.serpies.talk2me.models.ErrorResponseDto;
import com.serpies.talk2me.utilities.exceptions.EmailAlreadyExistsException;
import com.serpies.talk2me.utilities.exceptions.IncorrectPasswordOfUserException;
import com.serpies.talk2me.utilities.exceptions.TimeOutLoginException;
import com.serpies.talk2me.utilities.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFound(UserNotFoundException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto("USER_NOT_FOUND", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleUserAlreaduExists(EmailAlreadyExistsException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto("EMAIL_ALREADY_EXISTS", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectPasswordOfUserException.class)
    public ResponseEntity<ErrorResponseDto> handleIncorrectPasswordOfUser(IncorrectPasswordOfUserException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto("INCORRECT_PASSWORD_OF_USER", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TimeOutLoginException.class)
    public ResponseEntity<ErrorResponseDto> handleTimeOutLogin(TimeOutLoginException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto("TIME_OUT_LOGIN", ex.getMessage(), ex.getTimeOut());
        return new ResponseEntity<>(errorResponse, HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto("INTERNAL_SERVER_ERROR", "Something went wrong");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
