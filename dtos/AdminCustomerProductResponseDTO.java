package com.Bootcamp.Project.Application.dtos;

import com.fasterxml.jackson.annotation.JsonFilter;

import java.util.List;

@JsonFilter(value = "responseDTOFilter")
public class AdminCustomerProductResponseDTO {

    private Long productId;
    private String productName;
    private String sellerName;
    private String sellerContact;
    private String brand;
    private String description;
    private Boolean cancellable;
    private Boolean returnable;
    private Boolean active;
    private Long categoryId;
    private String categoryName;
    private Long parentCategoryId;
   /* private String imagePath;*/
    List<ProductVariationResponseDTO> variationsList;

    /*public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }*/

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerContact() {
        return sellerContact;
    }

    public void setSellerContact(String sellerContact) {
        this.sellerContact = sellerContact;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCancellable() {
        return cancellable;
    }

    public void setCancellable(Boolean cancellable) {
        this.cancellable = cancellable;
    }

    public Boolean getReturnable() {
        return returnable;
    }

    public void setReturnable(Boolean returnable) {
        this.returnable = returnable;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public List<ProductVariationResponseDTO> getVariationsList() {
        return variationsList;
    }

    public void setVariationsList(List<ProductVariationResponseDTO> variationsList) {
        this.variationsList = variationsList;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
