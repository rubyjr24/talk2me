package com.serpies.talk2me.exceptions;

public class IncorrectPasswordOfUserException extends RuntimeException {
    public IncorrectPasswordOfUserException(String message) {
        super(message);
    }
}
