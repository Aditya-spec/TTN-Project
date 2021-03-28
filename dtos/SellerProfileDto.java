package com.Bootcamp.Project.Application.dtos;

import com.Bootcamp.Project.Application.enums.Label;

public class SellerProfileDto {
    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;

    private Long gstNumber;

    private String email;

    private boolean active;

    private String companyName;

    private String companyContact;

    private String imagePath;

  /*  private String city;
    private String state;
    private String country;
    private String addressLine;
    private int zipCode;

    private Label label;*/

private AddressDto addressDto;

    public AddressDto getAddressDto() {
        return addressDto;
    }

    public void setAddressDto(AddressDto addressDto) {
        this.addressDto = addressDto;
    }

/*  public String getCity() {
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
    }*/

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActive() {
        return active;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyContact() {
        return companyContact;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Long getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(Long gstNumber) {
        this.gstNumber = gstNumber;
    }

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

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCompanyContact(String companyContact) {
        this.companyContact = companyContact;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


}
