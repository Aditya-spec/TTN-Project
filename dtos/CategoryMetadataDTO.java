package com.Bootcamp.Project.Application.dtos;

import java.util.HashSet;
import java.util.Set;

public class CategoryMetadataDTO {
    private String name;
    Set<CategoryMetadataFieldValuesDTO> categoryMetadataFieldValuesDTOSet=new HashSet<>();

    public Set<CategoryMetadataFieldValuesDTO> getCategoryMetadataFieldValuesDTOSet() {
        return categoryMetadataFieldValuesDTOSet;
    }

    public void setCategoryMetadataFieldValuesDTOSet(Set<CategoryMetadataFieldValuesDTO> categoryMetadataFieldValuesDTOSet) {
        this.categoryMetadataFieldValuesDTOSet = categoryMetadataFieldValuesDTOSet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
