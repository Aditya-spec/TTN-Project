package com.Bootcamp.Project.Application.dtos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CategoryResponseDTO {
    private Long parentId;
    private String name;
    private List<CategoryAddDTO> parentCategory;
    private List<CategoryAddDTO> childCategory;
    private Set<CategoryMetadataDTO> categoryMetadataDTOSet=new HashSet<>();

    public Set<CategoryMetadataDTO> getCategoryMetadataDTOSet() {
        return categoryMetadataDTOSet;
    }

    public void setCategoryMetadataDTOSet(Set<CategoryMetadataDTO> categoryMetadataDTOSet) {
        this.categoryMetadataDTOSet = categoryMetadataDTOSet;
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
