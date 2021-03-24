package com.Bootcamp.Project.Application.repositories;

import com.Bootcamp.Project.Application.entities.Cart;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart,Integer> {
}
