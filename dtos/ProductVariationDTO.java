package com.Bootcamp.Project.Application.dtos;

import org.json.simple.JSONArray;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class ProductVariationDTO {
    @NotNull(message = "product id is necessary")
    private Long productId;

    @NotNull(message = "please provide image path")
    @Pattern(regexp = "([^\\s]+(\\.(?i)(jpg|png|jpeg|bmp))$)", message = "please provide a valid image in any of jpeg,png,bmp,jpg format")
    private String primaryImageName;

    @NotNull(message = "please provide the quantity")
    @Min(value = 0,message = "quantity must be 0 or more")
    private int quantityAvailable;

    @NotNull(message = "please provide the path")
    @Min(value = 0,message = "price must be 0 or more")
    private Double price;

    @NotNull(message = "please provide the metadata values")
    private JSONArray metadata;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getPrimaryImageName() {
        return primaryImageName;
    }

    public void setPrimaryImageName(String primaryImageName) {
        this.primaryImageName = primaryImageName;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public JSONArray getMetadata() {
        return metadata;
    }

    public void setMetadata(JSONArray metadata) {
        this.metadata = metadata;
    }
}
