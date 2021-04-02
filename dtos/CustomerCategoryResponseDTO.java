package com.Bootcamp.Project.Application.dtos;

import java.util.List;

public class CustomerCategoryResponseDTO {
    private String name;
    private List<CategoryAddDTO> childrenCategoryList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CategoryAddDTO> getChildrenCategoryList() {
        return childrenCategoryList;
    }

    public void setChildrenCategoryList(List<CategoryAddDTO> childrenCategoryList) {
        this.childrenCategoryList = childrenCategoryList;
    }
}
