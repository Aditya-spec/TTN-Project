package com.Bootcamp.Project.Application.repositories;

import com.Bootcamp.Project.Application.entities.ProductVariation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductVariationRepository extends CrudRepository<ProductVariation,Long> {

    @Query(value = " from ProductVariation p where p.product.id=:productId")
    List<ProductVariation> fetchVariations(@Param("productId") Long id);
}
