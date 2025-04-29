package com.serpies.talk2me.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config {

    @Value("${auth.type}")
    private String authType;

    @Value("${token.expiration_time}")
    private Long tokenExpirationTime;

    @Value("${token.secret_key}")
    private String tokenSecretKey;

    public String getAuthType() {
        return authType;
    }

    public Long getTokenExpirationTime() {
        return tokenExpirationTime;
    }

    public String getTokenSecretKey() {
        return tokenSecretKey;
    }
}
