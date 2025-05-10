package com.serpies.talk2me.db.dao;

import com.serpies.talk2me.db.entity.ChatUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IChatUserDao extends CrudRepository<ChatUser, Long> {
}
