package com.Bootcamp.Project.Application.dtos;

import java.util.ArrayList;
import java.util.List;


public class CategoryMetadataFieldDTO {
    private String name;
    List<CategoryMetadataFieldValuesDTO> categoryFieldValues =new ArrayList<>();

    public List<CategoryMetadataFieldValuesDTO> getCategoryFieldValues() {
        return categoryFieldValues;
    }

    public void setCategoryFieldValues(List<CategoryMetadataFieldValuesDTO> categoryFieldValues) {
        this.categoryFieldValues = categoryFieldValues;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
