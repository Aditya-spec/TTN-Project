package com.Bootcamp.Project.Application.dtos;

import java.util.Set;

public class CategoryDTO extends BaseDomainDTO {
    private int parentId;
    private String name;
    private Set<CategoryMetadataFieldValuesDTO> categoryMetadataFieldValuesDTOSet;
    private Set<ProductDTO> productDTOSet;

    public CategoryDTO() {
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CategoryMetadataFieldValuesDTO> getCategoryMetadataFieldValuesDtoSet() {
        return categoryMetadataFieldValuesDTOSet;
    }

    public void setCategoryMetadataFieldValuesDtoSet(Set<CategoryMetadataFieldValuesDTO> categoryMetadataFieldValuesDTOSet) {
        this.categoryMetadataFieldValuesDTOSet = categoryMetadataFieldValuesDTOSet;
    }

    public Set<ProductDTO> getProductDtoSet() {
        return productDTOSet;
    }

    public void setProductDtoSet(Set<ProductDTO> productDTOSet) {
        this.productDTOSet = productDTOSet;
    }
}
