package com.Bootcamp.Project.Application.repositories;

import com.Bootcamp.Project.Application.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User,Long> {

    User findByEmail(String email);

    User findByResetToken(String token);
}
