package com.serpies.talk2me.config;

import com.serpies.talk2me.db.daos.IAuthTokenDao;
import com.serpies.talk2me.db.entities.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableScheduling
public class ScheduledConfig {

    @Autowired
    private IAuthTokenDao authTokenDao;

    @Scheduled(cron = "0 0 * * * ?") // Cada hora
    public void deleteExpiredAuthTokens() {
        List<AuthToken> authTokens = authTokenDao.findAllExpiredAuthTokens();
        this.authTokenDao.deleteAll(authTokens);
    }

}
