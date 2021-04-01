package com.Bootcamp.Project.Application.dtos;

import com.Bootcamp.Project.Application.enums.Label;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class AddressDTO extends BaseDomainDTO {

    @NotNull(message = "pls add City name")
    @Size(min = 3,max = 20,message = "city be between 3 to 20 characters")
    private String city;
    @NotNull(message = "pls add State name")
    @Size(min = 3,max = 20,message = "state be between 3 to 20 characters")
    private String state;
    @NotNull(message = "pls add Country name")
    @Size(min = 3,max = 20,message = "country be between 3 to 20 characters")
    private String country;
    @NotNull(message = "pls add AddressLine name")
    @Size(min = 3,max = 20,message = "addressLine be between 3 to 20 characters")
    private String addressLine;
    @NotNull(message = "pls add Zipcode name")
    private int zipCode;
    @NotNull(message = "pls add Address type")
    private Label label;


 /*   private UserDTO user;*/

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

   }
