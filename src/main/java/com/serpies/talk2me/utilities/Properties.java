package com.serpies.talk2me.utilities;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Properties {

    @Value("${auth.type}")
    private String authType;

    @Value("${auth.time_out}")
    private Long tokenTimeOut;

    @Value("${auth.attempts_before_time_out}")
    private Integer tokenAttemptsBeforeTimeOut;

    @Value("${token.secret_key}")
    private String tokenSecretKey;

    @Value("${token.expiration_time}")
    private Long tokenExpirationTime;

    @Value("${images.path}")
    private String imagesPath;

    public String getAuthType() {
        return authType;
    }

    public Long getTokenExpirationTime() {
        return tokenExpirationTime;
    }

    public String getTokenSecretKey() {
        return tokenSecretKey;
    }

    public Long getTokenTimeOut() {
        return tokenTimeOut;
    }

    public Integer getTokenAttemptsBeforeTimeOut() {
        return tokenAttemptsBeforeTimeOut;
    }

    public String getImagesPath() {
        return imagesPath;
    }
}
