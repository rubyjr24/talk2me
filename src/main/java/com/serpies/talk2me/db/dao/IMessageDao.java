package com.serpies.talk2me.db.dao;

import com.serpies.talk2me.db.dto.MessageDto;
import com.serpies.talk2me.db.entity.Message;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface IMessageDao extends CrudRepository<Message, Long> {

    @NativeQuery("SELECT m.id AS id, m.chat_id AS chatId, m.user_id AS userId, m.created_at AS createdAt, m.importance AS importance, tm.message AS message, f.uri AS uri FROM messages m LEFT JOIN text_messages tm ON m.id = tm.message_id LEFT JOIN file_messages fm ON m.id = fm.message_id LEFT JOIN files f ON f.id = fm.file_id WHERE m.chat_id = :chatId ORDER BY m.created_at ASC LIMIT :limit")
    List<Map<String, Object>> findAllByChatIdWithLimit(@Param("chatId") Long chatId, @Param("limit") Integer limit);

}
