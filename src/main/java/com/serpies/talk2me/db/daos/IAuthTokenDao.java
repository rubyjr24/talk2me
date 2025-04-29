package com.serpies.talk2me.db.daos;

import com.serpies.talk2me.db.entities.AuthToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAuthTokenDao extends CrudRepository<AuthToken, Long> {

    @Query("SELECT a FROM AuthToken a WHERE a.expiresAt < CURRENT_TIMESTAMP")
    List<AuthToken> findAllExpiredAuthTokens();

}
