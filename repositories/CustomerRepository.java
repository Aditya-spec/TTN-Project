package com.Bootcamp.Project.Application.repositories;

import com.Bootcamp.Project.Application.entities.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer,Integer> {
}
