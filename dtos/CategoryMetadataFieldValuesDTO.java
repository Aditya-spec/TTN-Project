package com.Bootcamp.Project.Application.dtos;

import org.json.simple.JSONArray;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class CategoryMetadataFieldValuesDTO {

    @NotNull(message = "metadataField cannot ne null")
    private Long categoryMetadataFieldId;
    @NotNull(message = "category cannot be nll")
    private Long categoryId;

    private List<String> fieldValues=new ArrayList<>();

    public Long getCategoryMetadataFieldId() {
        return categoryMetadataFieldId;
    }

    public void setCategoryMetadataFieldId(Long categoryMetadataFieldId) {
        this.categoryMetadataFieldId = categoryMetadataFieldId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<String> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(List<String> fieldValues) {
        this.fieldValues = fieldValues;
    }

}
