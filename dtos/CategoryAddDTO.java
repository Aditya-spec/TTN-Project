package com.Bootcamp.Project.Application.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


public class CategoryAddDTO {


    private Long parentId;

    @NotNull(message = "Category name cannot be null")
    @Size(min = 3,max = 25,message = "category must be in between 3 to 35 characters")
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
