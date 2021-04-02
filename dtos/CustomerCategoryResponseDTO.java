package com.Bootcamp.Project.Application.dtos;

import java.util.List;

public class CustomerCategoryResponseDTO {
    private String name;
    private List<CategoryAddDTO> childCategory;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CategoryAddDTO> getChildCategory() {
        return childCategory;
    }

    public void setChildCategory(List<CategoryAddDTO> childCategory) {
        this.childCategory = childCategory;
    }
}
