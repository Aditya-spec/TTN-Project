package com.Bootcamp.Project.Application.repositories;

import com.Bootcamp.Project.Application.entities.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleRepository extends CrudRepository<Role,Long> {

}
