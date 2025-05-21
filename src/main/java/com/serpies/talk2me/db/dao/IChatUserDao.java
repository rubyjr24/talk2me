package com.serpies.talk2me.db.dao;

import com.serpies.talk2me.db.entity.ChatUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IChatUserDao extends CrudRepository<ChatUser, Long> {

    @Query("SELECT COUNT(cu) > 0 FROM ChatUser cu WHERE cu.userId = :userId AND cu.chatId = :chatId")
    boolean userExistsInChat(@Param("userId") Long userId, @Param("chatId") Long chatId);

}
