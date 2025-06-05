package com.serpies.talk2me.db.dao;

import com.serpies.talk2me.db.entity.Chat;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface IChatDao extends CrudRepository<Chat, Long> {

    @Query("SELECT c FROM Chat c JOIN FETCH c.chatUserList WHERE c.id = :chatId")
    Optional<Chat> findByIdFechingUsers(@Param("chatId") Long chatId);

    @NativeQuery("SELECT c.* FROM chats c INNER JOIN chat_users cu ON c.id = cu.chat_id WHERE cu.user_id = :userId")
    List<Chat> findByUserId(@Param("userId") Long userId);

    @Query("SELECT DISTINCT c FROM Chat c WHERE c.id IN (SELECT c2.id FROM Chat c2 JOIN c2.chatUserList cu2 WHERE cu2.userId = :userId)")
    List<Chat> findByUserIdFechingUsers(@Param("userId") Long userId);

}
