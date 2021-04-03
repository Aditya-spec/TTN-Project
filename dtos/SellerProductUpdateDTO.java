package com.Bootcamp.Project.Application.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SellerProductUpdateDTO {



    private String name;

   private String description;

    @JsonProperty
    private boolean cancellable;

    @JsonProperty
    private boolean returnable;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getCancellable() {
        return cancellable;
    }

    public void setCancellable(boolean cancellable) {
        this.cancellable = cancellable;
    }

    public boolean getReturnable() {
        return returnable;
    }

    public void setReturnable(boolean returnable) {
        this.returnable = returnable;
    }
}
