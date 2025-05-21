package com.serpies.talk2me.exceptions;

public class UserNotInChatException extends RuntimeException {
    public UserNotInChatException(String message) {
        super(message);
    }
}
