package com.Bootcamp.Project.Application.dtos;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class DirectOrderDTO {
    @NotNull(message = "addressId cannot be null")
    Long addressId;

    @NotNull(message = "variationId cannot be null")
    Long variationId;

    @NotNull(message = "please provide the quantity")
    @Min(value = 0, message = "quantity must be 0 or more")
    Integer quantity;

    @NotNull(message = "payment method cannot be null")
    String paymentMethod;

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getVariationId() {
        return variationId;
    }

    public void setVariationId(Long variationId) {
        this.variationId = variationId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
