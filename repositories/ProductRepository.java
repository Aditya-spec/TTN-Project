package com.Bootcamp.Project.Application.repositories;

import com.Bootcamp.Project.Application.entities.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product,Integer> {
}
