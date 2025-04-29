package com.serpies.talk2me.db.dtos;

import java.util.Date;

public class AuthTokenDto {

    private String token;
    private Date expiresAt;

    public AuthTokenDto() {
    }

    public AuthTokenDto(String token, Date expiresAt) {
        this.token = token;
        this.expiresAt = expiresAt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }
}
