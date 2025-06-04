package com.serpies.talk2me.utilities.auth;

import com.serpies.talk2me.db.dao.IAuthTokenDao;
import com.serpies.talk2me.exceptions.NotValidTokenException;
import com.serpies.talk2me.utilities.Assert;
import com.serpies.talk2me.utilities.Properties;
import com.serpies.talk2me.db.entity.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Component
public class AuthUtil {

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";

    @Autowired
    private Properties properties;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private IAuthTokenDao authTokenDao;

    public boolean hasTimeOut(AuthToken authToken){

        Date now = new Date();

        long secondsTimeOut = (authToken.getUnsuccessfulAttempts() / properties.getTokenAttemptsBeforeTimeOut()) * properties.getTokenTimeOut();
        Instant timeOut = authToken.getLastAttempt().toInstant().plusSeconds(secondsTimeOut);

        return authToken.getUnsuccessfulAttempts() != 0
                && authToken.getUnsuccessfulAttempts() % 3 == 0
                && now.before(Date.from(timeOut));
    }

    public Long validateAndGetUser(String token){

        NotValidTokenException exception = new NotValidTokenException("The token must be valid");

        Assert.ifCondition(!this.jwtUtil.isTokenValid(token), exception);
        Long userId = this.jwtUtil.getUserId(token);

        Optional<AuthToken> authTokenOptional = this.authTokenDao.findById(userId);
        Assert.ifCondition(authTokenOptional.isEmpty(), exception);
        return userId;
    }

    public String getTokenFromAuthorization(String headerValue){
        return headerValue.replaceFirst(BEARER, "");
    }

}
