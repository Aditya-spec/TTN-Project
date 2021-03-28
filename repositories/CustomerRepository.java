package com.Bootcamp.Project.Application.repositories;

import com.Bootcamp.Project.Application.entities.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer,Integer> {

    @Query("from Customer")
    List<Customer> fetchAllCustomer();

    Customer findByName(String name);

    Customer findByActivationToken(String token);

    Customer findByEmail(String email);

    Customer findById(long id);

    @Query("from Customer")
    List<Customer> fetchCustomerByPage(Pageable sortById);


}
