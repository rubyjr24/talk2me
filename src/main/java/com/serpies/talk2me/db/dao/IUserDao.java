package com.serpies.talk2me.db.dao;

import com.serpies.talk2me.db.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface IUserDao extends CrudRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = :email")
    Boolean existsByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.id IN :userIds ORDER BY u.id")
    List<User> findUsersByUserIds(@Param("userIds") Set<Long> userIds);

    @Query("SELECT u FROM User u WHERE u.email IN :userEmails ORDER BY u.id")
    List<User> findUsersByUserEmails(@Param("userEmails") Set<String> userEmails);

}
