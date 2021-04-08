package com.Bootcamp.Project.Application.dtos;



import org.springframework.validation.annotation.Validated;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


import java.util.ArrayList;
import java.util.List;

@Validated
public class CategoryMetadataFieldValuesDTO {

    @NotNull(message = "metadataField cannot ne null")
    private Long categoryMetadataFieldId;
    @NotNull(message = "category cannot be nll")
    private Long categoryId;

    @NotEmpty(message = "MetadataField values need to be present")
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
