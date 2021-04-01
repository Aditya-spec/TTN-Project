package com.Bootcamp.Project.Application.repositories;

import com.Bootcamp.Project.Application.dtos.CategoryAddDTO;
import com.Bootcamp.Project.Application.entities.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category,Long> {
    Category findByName(String name);

    @Query("from Category")
    Optional<List<Category>> fetchALlCategories(Pageable sortById);

    Optional<Category> findById(Long id);

    @Query(nativeQuery = true,value = "select * from category c where c.parent_id=:parent_id")
    Optional<List<Category>> findNextChildren(@Param("parent_id") Long parent_id);


}
