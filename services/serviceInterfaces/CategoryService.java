package com.Bootcamp.Project.Application.services.serviceInterfaces;

import com.Bootcamp.Project.Application.dtos.*;

import java.util.List;

public interface CategoryService {

    String addCategory(CategoryAddDTO categoryAddDTO);

    List<CategoryResponseDTO> showCategories(int offset, int size);

    CategoryResponseDTO showCategory(Long id);

    String addMetadata(String metaData);

    List<CMDResponseDTO> showMetaData(int offset, int size);

    String addMetadataValues(CategoryMetadataFieldValuesDTO categoryMetadataFieldValuesDTO);

    String updateCategory(Long id, String updatedName);

    String updateMetadataValues(CategoryMetadataFieldValuesDTO categoryMetadataFieldValuesDTO);

    List<SellerCategoryResponseDTO> showSellerCategories();

    List<CustomerCategoryResponseDTO> showCustomerCategories();

    CustomerCategoryResponseDTO showCustomerCategoriesParam(Long id);

    CustomerCategoryFilterDTO filter(Long categoryId);
}
