package com.Bootcamp.Project.Application.repositories;

import com.Bootcamp.Project.Application.entities.categoryMetadataFieldValues.CategoryMetadataFieldValues;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryMetadataFieldValuesRepository extends CrudRepository<CategoryMetadataFieldValues,Integer> {
    @Query(value = "select * from category_metadata_field_values cmfv where cmfv.category_id=:catId and cmfv.category_metadata_field_id=:metaId",nativeQuery = true )
    String fetchMetaDataValues(@Param("catId") Long categoryId ,@Param("metaId") Long metaId);
}
