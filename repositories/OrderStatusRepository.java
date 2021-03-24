package com.Bootcamp.Project.Application.repositories;

import com.Bootcamp.Project.Application.entities.OrderStatus;
import org.springframework.data.repository.CrudRepository;

public interface OrderStatusRepository extends CrudRepository<OrderStatus,Integer> {
}
