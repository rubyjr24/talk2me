package com.serpies.talk2me.db.dto;

import java.util.Date;

public class AuthTokenDto {

    private Long userId;
    private String token;
    private Date expiresAt;

    public AuthTokenDto() {
    }

    public AuthTokenDto(Long userId, String token, Date expiresAt) {
        this.userId = userId;
        this.token = token;
        this.expiresAt = expiresAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
