package com.Bootcamp.Project.Application.repositories;

import com.Bootcamp.Project.Application.entities.ProductReview;
import org.springframework.data.repository.CrudRepository;

public interface ProductReviewRepository extends CrudRepository<ProductReview,Integer> {
}
