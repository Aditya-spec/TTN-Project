package com.Bootcamp.Project.Application.dtos;

import java.util.ArrayList;
import java.util.List;


public class CategoryMetadataFieldDTO {
    private String name;
    List<CategoryMetadataFieldValuesDTO> categoryMetadataFieldValuesDTOSet=new ArrayList<>();

    public List<CategoryMetadataFieldValuesDTO> getCategoryMetadataFieldValuesDTOSet() {
        return categoryMetadataFieldValuesDTOSet;
    }

    public void setCategoryMetadataFieldValuesDTOSet(List<CategoryMetadataFieldValuesDTO> categoryMetadataFieldValuesDTOSet) {
        this.categoryMetadataFieldValuesDTOSet = categoryMetadataFieldValuesDTOSet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
