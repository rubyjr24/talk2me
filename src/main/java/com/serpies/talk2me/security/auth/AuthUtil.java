package com.serpies.talk2me.security.auth;

import com.serpies.talk2me.utilities.Properties;
import com.serpies.talk2me.db.entity.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class AuthUtil {

    @Autowired
    private Properties properties;

    public boolean hasTimeOut(AuthToken authToken){

        Date now = new Date();

        long secondsTimeOut = (authToken.getUnsuccessfulAttempts() / properties.getTokenAttemptsBeforeTimeOut()) * properties.getTokenTimeOut();
        Instant timeOut = authToken.getLastAttempt().toInstant().plusSeconds(secondsTimeOut);

        return authToken.getUnsuccessfulAttempts() != 0
                && authToken.getUnsuccessfulAttempts() % 3 == 0
                && now.before(Date.from(timeOut));
    }

}
