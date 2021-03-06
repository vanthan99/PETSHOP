package com.cdio.petshop.repositories;

import com.cdio.petshop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    @Query("select u from User u where u.username = :username and u.enable = 2 ")
    User getUserByUsername(@Param("username") String username);

    @Transactional
    @Modifying
    @Query("update User u set u.password=:password where u.username=:username")
    void changePassword(String username,String password);
}
