package com.Bootcamp.Project.Application.dtos;

import java.util.List;

public class SellerCategoryResponseDTO {
    private String name;
    private List<CategoryAddDTO> parentList;
    private List<CMDResponseDTO> fieldValues;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CategoryAddDTO> getParentList() {
        return parentList;
    }

    public void setParentList(List<CategoryAddDTO> parentList) {
        this.parentList = parentList;
    }

    public List<CMDResponseDTO> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(List<CMDResponseDTO> fieldValues) {
        this.fieldValues = fieldValues;
    }
}
