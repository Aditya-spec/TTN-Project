package com.Bootcamp.Project.Application.repositories;

import com.Bootcamp.Project.Application.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {
}
