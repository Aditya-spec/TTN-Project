package com.Bootcamp.Project.Application.dtos;

import java.util.ArrayList;
import java.util.List;

public class CategoryResponseDTO {
    private Long parentId;
    private String name;
    private List<CategoryAddDTO> parentCategory;
    private List<CategoryAddDTO> childCategory;
    private List<CMDResponseDTO> fieldValues =new ArrayList<>();

    public List<CMDResponseDTO> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(List<CMDResponseDTO> fieldValues) {
        this.fieldValues = fieldValues;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CategoryAddDTO> getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(List<CategoryAddDTO> parentCategory) {
        this.parentCategory = parentCategory;
    }

    public List<CategoryAddDTO> getChildCategory() {
        return childCategory;
    }

    public void setChildCategory(List<CategoryAddDTO> childCategory) {
        this.childCategory = childCategory;
    }
}
