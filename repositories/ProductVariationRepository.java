package com.Bootcamp.Project.Application.repositories;

import com.Bootcamp.Project.Application.entities.ProductVariation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductVariationRepository extends CrudRepository<ProductVariation,Long> {

    @Query(value = " from ProductVariation p where p.product.id=:productId")
    List<ProductVariation> fetchVariations(@Param("productId") Long id);

    @Query(value = "select MIN(price) from product_variation where product_id IN (select product_id from product where category_id=:category_id)",nativeQuery = true)
    Double getMaxPrice(@Param(("category_id")) Long categoryId);

    @Query(value = "select MAX(price) from product_variation where product_id IN (select product_id from product where category_id=:category_id)",nativeQuery = true)
    Double getMinPrice(@Param(("category_id")) Long categoryId);
}




