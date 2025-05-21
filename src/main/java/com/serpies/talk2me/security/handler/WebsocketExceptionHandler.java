package com.serpies.talk2me.security.handler;

import com.serpies.talk2me.exceptions.InternalServerError;
import com.serpies.talk2me.model.ErrorResponseDto;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class WebsocketExceptionHandler {

    private static final String ERROR_PATH = "/private/errors";

    @MessageExceptionHandler(IllegalArgumentException.class)
    @SendToUser(ERROR_PATH)
    public ErrorResponseDto handle(IllegalArgumentException ex) {
        return new ErrorResponseDto("MALFORMED_REQUEST", ex.getMessage());
    }

    @MessageExceptionHandler(InternalServerError.class)
    @SendToUser(ERROR_PATH)
    public ErrorResponseDto handle(InternalServerError ex) {
        return new ErrorResponseDto("INTERNAL_SERVER_ERROR", ex.getMessage());
    }

    @MessageExceptionHandler(Exception.class)
    @SendToUser(ERROR_PATH)
    public ErrorResponseDto handle(Exception ex) {
        return new ErrorResponseDto("INTERNAL_SERVER_ERROR", "Something went wrong");
    }

}
