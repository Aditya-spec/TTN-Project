package com.Bootcamp.Project.Application.dtos;

import com.Bootcamp.Project.Application.enums.Label;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SellerProfileDTO {
    private Long id;

    @Size(min = 2, max = 20, message = "your firstName should have 2 to 20 characters")
    private String firstName;
    @Size(min = 2, max = 20, message = "your middleName should have 2 to 20 characters")
    private String middleName;
    @Size(min = 2, max = 20, message = "your lastName should have 2 to 20 characters")
    private String lastName;

    @Pattern(regexp = "^[0-9]{2}[A-Z]{5}[0-9]{4}"
            + "[A-Z]{1}[1-9A-Z]{1}"
            + "Z[0-9A-Z]{1}$", message = "give in proper GST format")
    private String gstNumber;

    @Email
    private String email;

    private boolean active;
    @Size(min = 5, max = 30, message = "company name should have 5 to 30 characters")
    private String companyName;

    @Pattern(regexp = "((\\+*)((0[ -]+)*|(91 )*)(\\d{12}+|\\d{10}+))|\\d{5}([- ]*)\\d{6}", message = "enter a valid phone number")
    private String companyContact;

    @Pattern(regexp = "([^\\s]+(\\.(?i)(jpg|png|jpeg|bmp))$)", message = "please provide a valid image in any of jpeg,png,bmp,jpg format")
    private String imagePath;

    @Size(min = 3,max = 20,message = "must be between 3 to 20 characters")
    private String city;
    @Size(min = 3,max = 20,message = "must be between 3 to 20 characters")
    private String state;
    @Size(min = 3,max = 20,message = "must be between 3 to 20 characters")
    private String country;
    @Size(min = 3,max = 20,message = "must be between 3 to 20 characters")
    private String addressLine;
    @Size(min = 3,max = 20,message = "must be between 3 to 20 characters")
    private int zipCode;

    private Label label;

    private AddressDTO addressDto;

    public AddressDTO getAddressDto() {
        return addressDto;
    }

    public void setAddressDto(AddressDTO addressDto) {
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

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
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
