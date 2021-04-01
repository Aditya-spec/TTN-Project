package com.Bootcamp.Project.Application.dtos;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

public class CategoryAddDTO {

    private Long parentId;

    @NotNull(message = "Category name cannot be null")
    private String name;

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
}
