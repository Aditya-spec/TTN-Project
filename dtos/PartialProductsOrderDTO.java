package com.Bootcamp.Project.Application.dtos;

import java.util.List;
public class PartialProductsOrderDTO {
    List<Long> variationIdList;
    String paymentMethod;

    public List<Long> getVariationIdList() {
        return variationIdList;
    }

    public void setVariationIdList(List<Long> variationIdList) {
        this.variationIdList = variationIdList;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
