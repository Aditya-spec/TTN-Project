package com.Bootcamp.Project.Application.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SellerProductAddDTO {
    @NotNull(message = " category id for the product is a necessary field")
    private Long category;

    @NotNull(message = " product name is a necessary  field")
    private String name;

    @NotNull(message = " brand name of a product is a necessary field")
    private String brand;

    @Size(min = 5,max = 50,message = "description must have characters in range from 5 to 50")
    private String description;

    @JsonProperty
    private Boolean cancellable;

    @JsonProperty
    private Boolean returnable;



    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
