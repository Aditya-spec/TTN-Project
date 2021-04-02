package com.Bootcamp.Project.Application.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


public class CategoryAddDTO {

    private Long parentId;

    @NotNull(message = "Category name cannot be null")
    private String name;

    @JsonIgnore
    private List<CMDResponseDTO> categoryMetadataFieldDTOSet =new ArrayList<>();

    public List<CMDResponseDTO> getCategoryMetadataFieldDTOSet() {
        return categoryMetadataFieldDTOSet;
    }

    public void setCategoryMetadataFieldDTOSet(List<CMDResponseDTO> categoryMetadataFieldDTOSet) {
        this.categoryMetadataFieldDTOSet = categoryMetadataFieldDTOSet;
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
