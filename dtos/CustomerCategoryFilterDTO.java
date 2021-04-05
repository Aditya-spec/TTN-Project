package com.Bootcamp.Project.Application.dtos;

import java.util.List;

public class CustomerCategoryFilterDTO {
    private Long categoryId;

    private String categoryName;

    private List<CMDResponseDTO> fieldValuesList;

    private List<String> brandNames;

    private Double maxPrice;

    private Double minPrice;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<CMDResponseDTO> getFieldValuesList() {
        return fieldValuesList;
    }

    public void setFieldValuesList(List<CMDResponseDTO> fieldValuesList) {
        this.fieldValuesList = fieldValuesList;
    }

    public List<String> getBrandNames() {
        return brandNames;
    }

    public void setBrandNames(List<String> brandNames) {
        this.brandNames = brandNames;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }
}
