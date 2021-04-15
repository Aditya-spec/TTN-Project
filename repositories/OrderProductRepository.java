package com.Bootcamp.Project.Application.repositories;

import com.Bootcamp.Project.Application.entities.OrderProduct;
import org.springframework.data.repository.CrudRepository;

public interface OrderProductRepository extends CrudRepository<OrderProduct,Long> {
}
