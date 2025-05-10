package com.serpies.talk2me.db.dao;

import com.serpies.talk2me.db.entity.ChatMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IChatMessage extends CrudRepository<ChatMessage, Long> {
}
