package com.serpies.talk2me.db.dao;

import com.serpies.talk2me.db.entity.Chat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IChatDao extends CrudRepository<Chat, Long> {

}
