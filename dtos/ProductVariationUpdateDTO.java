package com.Bootcamp.Project.Application.dtos;

import org.json.simple.JSONArray;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class ProductVariationUpdateDTO {



    @Pattern(regexp = "([^\\\\s]+(\\\\.(?i)(jpg|png|jpeg|bmp))$)",message = "please provide the valid image format")
    private String primaryImageName;

    @Min(value = 0,message = "quantity must be 0 or more")
    private int quantityAvailable;

    @Min(value = 0,message = "price must be 0 or more")
    private Double price;

    private JSONArray metadata;

    private Boolean active;



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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
