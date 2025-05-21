package com.serpies.talk2me.db.dao;

import com.serpies.talk2me.db.entity.FileMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFileMessageDao extends CrudRepository<FileMessage, Long> {
}
