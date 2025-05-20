package com.serpies.talk2me.exceptions;

import java.util.Date;

public class TimeOutLoginException extends RuntimeException {

    private final Date timeOut;

    public TimeOutLoginException(String message, Date timeOut) {
        super(message);
        this.timeOut = timeOut;
    }

    public Date getTimeOut() {
        return timeOut;
    }
}
