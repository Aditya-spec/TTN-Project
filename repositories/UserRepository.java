package com.Bootcamp.Project.Application.repositories;

import com.Bootcamp.Project.Application.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);

    User findByResetToken(String token);

    @Modifying
    @Query("UPDATE User u SET u.failedAttempt = ?1 WHERE u.email = ?2")
    public void updateFailedAttempts(int failAttempts, String email);
}
