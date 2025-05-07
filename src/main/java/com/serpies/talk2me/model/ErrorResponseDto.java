package com.serpies.talk2me.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDto {

    private String code;
    private String message;
    private Date timeOut;

    public ErrorResponseDto() {
    }

    public ErrorResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorResponseDto(String code, String message, Date timeOut) {
        this.code = code;
        this.message = message;
        this.timeOut = timeOut;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Date timeOut) {
        this.timeOut = timeOut;
    }
}
