package com.Bootcamp.Project.Application.repositories;

import com.Bootcamp.Project.Application.entities.Cart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends CrudRepository<Cart, Long> {
    @Query(value = "select * from cart c where c.customer_id=:customerId and c.product_variation_id=:variationId", nativeQuery = true)
    Cart fetchCartByCustomerAndVariation(@Param("customerId") Long customerId, @Param("variationId") Long variationId);

    @Query(value = "select * from cart c where c.customer_id=:customerId", nativeQuery = true)
    List<Cart> fetchByCustomer(@Param("customerId") Long id);
}
