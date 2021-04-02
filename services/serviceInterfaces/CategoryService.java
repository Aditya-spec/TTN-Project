package com.Bootcamp.Project.Application.services.serviceInterfaces;

import com.Bootcamp.Project.Application.dtos.CategoryAddDTO;
import com.Bootcamp.Project.Application.dtos.CategoryMetadataFieldDTO;
import com.Bootcamp.Project.Application.dtos.CategoryMetadataFieldValuesDTO;
import com.Bootcamp.Project.Application.dtos.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {

    String addCategory(CategoryAddDTO categoryAddDTO);

    List<CategoryResponseDTO> showCategories();

    CategoryResponseDTO showCategory(Long id);

    String addMetadata(String metaData);

    List<CategoryMetadataFieldDTO> showMetaData();

    String addMetadataValues(CategoryMetadataFieldValuesDTO categoryMetadataFieldValuesDTO);
}
