package com.Bootcamp.Project.Application.dtos;

import com.Bootcamp.Project.Application.enums.Label;
import javax.validation.constraints.Size;


public class AddressUpdateDTO {


    @Size(min=3,max=25,message = "city must have characters between 2 to 20")
    private String city;
    @Size(min=3,max=25,message = "state must have characters between 2 to 20")
    private String state;
    @Size(min=3,max=25,message = "country must have characters between 2 to 20")
    private String country;
    @Size(min=3,max=25,message = "addressLine must have characters between 2 to 20")
    private String addressLine;
    @Size(min = 6,max = 6,message = "zipCode must be of 6 digits")
    private String zipCode;

    private Label label;

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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }
}
