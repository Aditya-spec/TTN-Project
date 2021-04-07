package com.Bootcamp.Project.Application.dtos;


import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Validated
public class CategoryMetadataFieldValuesDTO {

    @NotNull(message = "metadataField cannot ne null")
    private Long categoryMetadataFieldId;
    @NotNull(message = "category cannot be nll")
    private Long categoryId;

    @NotNull(message = "MetadataField values need to be present")
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
