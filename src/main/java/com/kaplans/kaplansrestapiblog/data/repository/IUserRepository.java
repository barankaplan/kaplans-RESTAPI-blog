package com.kaplans.kaplansrestapiblog.data.repository;


import com.kaplans.kaplansrestapiblog.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.eMail = ?1")
    Optional<User> findByEMail(String eMail);


    @Query("select u from User u where u.userName = ?1 or u.eMail = ?1")
    Optional<User> findByUserNameOrEMail(String userNameOrEmail);


    @Query("select u from User u where u.userName = ?1")
    Optional<User> findByUserName(String userName);

    @Query("select (count(u) > 0) from User u where u.userName = ?1")
    Boolean existsByUserName(String userName);

    @Query("select (count(u) > 0) from User u where u.eMail = ?1")
    Boolean existsByEMail(String eMail);


}