package com.Bootcamp.Project.Application.repositories;

import com.Bootcamp.Project.Application.entities.categoryMetadataFieldValues.CategoryMetadataFieldValues;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CMFVRepository extends CrudRepository<CategoryMetadataFieldValues, Long> {
    @Query(value = "select * from category_metadata_field_values c where c.category_id=:id",nativeQuery = true)
    List<CategoryMetadataFieldValues> fetchByCategoryId(@Param("id") Long id);
}
