package com.Bootcamp.Project.Application.dtos;

import com.Bootcamp.Project.Application.entities.categoryMetadataFieldValues.CategoryMetadataFieldValues;

import java.util.Set;

public class CategoryDto extends BaseDomainDto{
    private int parentId;
    private String name;
    private Set<CategoryMetadataFieldValuesDto> categoryMetadataFieldValuesDtoSet;
    private Set<ProductDto> productDtoSet;

    public CategoryDto() {
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

    public Set<CategoryMetadataFieldValuesDto> getCategoryMetadataFieldValuesDtoSet() {
        return categoryMetadataFieldValuesDtoSet;
    }

    public void setCategoryMetadataFieldValuesDtoSet(Set<CategoryMetadataFieldValuesDto> categoryMetadataFieldValuesDtoSet) {
        this.categoryMetadataFieldValuesDtoSet = categoryMetadataFieldValuesDtoSet;
    }

    public Set<ProductDto> getProductDtoSet() {
        return productDtoSet;
    }

    public void setProductDtoSet(Set<ProductDto> productDtoSet) {
        this.productDtoSet = productDtoSet;
    }
}
