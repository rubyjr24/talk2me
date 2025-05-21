package com.serpies.talk2me.db.dao;

import com.serpies.talk2me.db.entity.File;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFileDao extends CrudRepository<File, Long> {

}
