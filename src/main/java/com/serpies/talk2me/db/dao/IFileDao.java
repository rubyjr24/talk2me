package com.serpies.talk2me.db.dao;

import com.serpies.talk2me.db.entity.File;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IFileDao extends CrudRepository<File, Long> {

    @NativeQuery("""
            SELECT DISTINCT f.*
                    FROM files f
                    INNER JOIN file_messages fm ON fm.file_id = f.id
                    INNER JOIN messages m ON m.id = fm.message_id
                    INNER JOIN chat_users cu ON cu.user_id IN (
                    	SELECT cu.user_id
                    	FROM chat_users cu2
                    	WHERE cu2.chat_id = cu.chat_id
                    )
                    WHERE m.id = 17 AND cu.user_id = 1;
    """)
    Optional<File> getFileByMessageIdAndUserId(@Param("userId") Long userId, @Param("messageId") Long messageId);

}
