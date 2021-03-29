package com.Bootcamp.Project.Application.dtos;

import com.Bootcamp.Project.Application.enums.Label;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;


public class AddressDto extends BaseDomainDto {

    @NotNull(message = "pls add City name")
    private String city;
    @NotNull(message = "pls add State name")
    private String state;
    @NotNull(message = "pls add Country name")
    private String country;
    @NotNull(message = "pls add AddressLine name")
    private String addressLine;
    @NotNull(message = "pls add Zipcode name")
    private int zipCode;
    @NotNull(message = "pls add Address type")
    private Label label;


    private UserDto user;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}