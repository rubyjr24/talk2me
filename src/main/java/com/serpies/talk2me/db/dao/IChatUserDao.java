package com.serpies.talk2me.db.dao;

import com.serpies.talk2me.db.entity.ChatUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IChatUserDao extends CrudRepository<ChatUser, Long> {

    @Query("SELECT cu FROM ChatUser cu WHERE cu.userId = :userId AND cu.chatId = :chatId")
    Optional<ChatUser> getChatUserByUserIdAndChatId(@Param("userId") Long userId, @Param("chatId") Long chatId);

}
