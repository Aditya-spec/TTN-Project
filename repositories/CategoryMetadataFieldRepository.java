package com.Bootcamp.Project.Application.repositories;

import com.Bootcamp.Project.Application.entities.CategoryMetadataField;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryMetadataFieldRepository extends JpaRepository<CategoryMetadataField, Long> {

    Optional<CategoryMetadataField> findByname(String metaData);

    @Query("from CategoryMetadataField")
    Optional<List<CategoryMetadataField>> fetchAll(Pageable sortById);
}
