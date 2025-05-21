package com.serpies.talk2me.db.dao;

import com.serpies.talk2me.db.entity.TextMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITextMessageDao extends CrudRepository<TextMessage, Long> {

}