package com.example.firstapi.Repositories;

import com.example.firstapi.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.chatId = :chatId")
    Optional<User> findUserByChatId(@Param("chatId") String chatId);

    Optional<User> findByChatId(String chatId);

    boolean existsUserByChatId(String chatId);

    int deleteByChatId(String chatId);

    @Modifying
    @Query("update User u set u.approve = :newStatus where u.id = :id")
    int updateApproveStatus(@Param("id") Long id, @Param("newStatus") boolean newStatus);

    @Modifying
    @Query("update User u set u.blackList = :newStatus where u.id = :id")
    int updateBlackList(@Param("id") Long id, @Param("newBlackList") boolean newBlackList);


}
