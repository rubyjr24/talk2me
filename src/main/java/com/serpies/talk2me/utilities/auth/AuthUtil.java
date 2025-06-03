package com.serpies.talk2me.utilities.auth;

import com.serpies.talk2me.exceptions.NotValidTokenException;
import com.serpies.talk2me.utilities.Assert;
import com.serpies.talk2me.utilities.Properties;
import com.serpies.talk2me.db.entity.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class AuthUtil {

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";

    @Autowired
    private Properties properties;

    @Autowired
    private JwtUtil jwtUtil;

    public boolean hasTimeOut(AuthToken authToken){

        Date now = new Date();

        long secondsTimeOut = (authToken.getUnsuccessfulAttempts() / properties.getTokenAttemptsBeforeTimeOut()) * properties.getTokenTimeOut();
        Instant timeOut = authToken.getLastAttempt().toInstant().plusSeconds(secondsTimeOut);

        return authToken.getUnsuccessfulAttempts() != 0
                && authToken.getUnsuccessfulAttempts() % 3 == 0
                && now.before(Date.from(timeOut));
    }

    public Long validateAndGetUser(String token){
        Assert.ifCondition(!this.jwtUtil.isTokenValid(token), new NotValidTokenException("The token must be valid"));
        return this.jwtUtil.getUserId(token);
    }

    public String getTokenFromAuthorization(String headerValue){
        return headerValue.replaceFirst(BEARER, "");
    }

}
