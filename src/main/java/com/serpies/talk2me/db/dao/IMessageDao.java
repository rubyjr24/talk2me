package com.serpies.talk2me.db.dao;

import com.serpies.talk2me.db.entity.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMessageDao extends CrudRepository<Message, Long> {

}
