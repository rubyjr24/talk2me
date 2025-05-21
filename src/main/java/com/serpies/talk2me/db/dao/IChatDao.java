package com.serpies.talk2me.db.dao;

import com.serpies.talk2me.db.entity.Chat;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface IChatDao extends CrudRepository<Chat, Long> {

    @Query("SELECT c FROM Chat c JOIN FETCH c.chatUserList WHERE c.id = :chatId")
    Optional<Chat> findByIdFechingUsers(@Param("chatId") Long chatId);

}
