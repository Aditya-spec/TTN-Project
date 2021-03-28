package com.Bootcamp.Project.Application.dtos;

import com.Bootcamp.Project.Application.enums.Label;

public class CustomerUpdateDto {
    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;

    private String email;

    private boolean active;

    private String imagePath;

    private String city;

    private String state;

    private String country;

    private String addressLine;

    private int zipCode;

    private Label label;

    private String contact;

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
