package com.Bootcamp.Project.Application.repositories;

import com.Bootcamp.Project.Application.entities.AuthenticatedToken;
import org.springframework.data.repository.CrudRepository;

public interface AuthenticatedTokenRepository extends CrudRepository<AuthenticatedToken,Long> {

    AuthenticatedToken findByUsername(String email);
}
