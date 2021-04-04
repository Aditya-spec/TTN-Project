package com.Bootcamp.Project.Application.repositories;

import com.Bootcamp.Project.Application.entities.ProductVariation;
import org.springframework.data.repository.CrudRepository;

public interface ProductVariationRepository extends CrudRepository<ProductVariation,Long> {
}
