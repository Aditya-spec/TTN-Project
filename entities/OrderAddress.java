package com.Bootcamp.Project.Application.entities;

import com.Bootcamp.Project.Application.enums.Label;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class OrderAddress {
    private String orderCity;
    private String orderState;
    private String orderCountry;
    private String orderAddressLine;
    private String orderZipCode;

    @Enumerated(EnumType.STRING)
    private Label orderLabel;

    //Getters

    public String getOrderCity() {
        return orderCity;
    }

    public String getOrderState() {
        return orderState;
    }

    public String getOrderCountry() {
        return orderCountry;
    }

    public String getOrderAddressLine() {
        return orderAddressLine;
    }

    public String getOrderZipCode() {
        return orderZipCode;
    }

    public Label getOrderLabel() {
        return orderLabel;
    }


    //Setters

    public void setOrderCity(String orderCity) {
        this.orderCity = orderCity;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public void setOrderCountry(String orderCountry) {
        this.orderCountry = orderCountry;
    }

    public void setOrderAddressLine(String orderAddressLine) {
        this.orderAddressLine = orderAddressLine;
    }

    public void setOrderZipCode(String orderZipCode) {
        this.orderZipCode = orderZipCode;
    }

    public void setOrderLabel(Label orderLabel) {
        this.orderLabel = orderLabel;
    }
}
