package com.Bootcamp.Project.Application.dtos;

import java.util.List;

public class SellerCategoryResponseDTO {
    private String name;
    private List<CategoryAddDTO> parent;
    private List<CMDResponseDTO> fieldValues;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CategoryAddDTO> getParent() {
        return parent;
    }

    public void setParent(List<CategoryAddDTO> parent) {
        this.parent = parent;
    }

    public List<CMDResponseDTO> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(List<CMDResponseDTO> fieldValues) {
        this.fieldValues = fieldValues;
    }
}
