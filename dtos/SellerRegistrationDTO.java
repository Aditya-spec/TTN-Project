package com.Bootcamp.Project.Application.dtos;

import com.Bootcamp.Project.Application.enums.Label;
import org.springframework.stereotype.Component;

import javax.validation.constraints.*;

@Component
public class SellerRegistrationDTO {

    @NotNull(message = "email is required and should be unique")
    @Email(message = "enter a valid email")
    private String email;

    @NotNull(message = "password is mandatory ")
    @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,15})", message = "Password must be 8 characters long and should contain uppercase,lowercase,digit,and special character")
    private String password;

    @NotNull(message = "password is mandatory ")
    @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,15})", message = "Password must be 8 characters long and should contain uppercase,lowercase,digit,and special character")
    private String confirmPassword;


    @NotEmpty(message = " first name is required ")
    @Size(min = 2, max = 20, message = "your firstName should have 2 to 20 characters")
    private String firstName;

    @NotNull(message = " first name is required ")
    @Size(min = 2, max = 20, message = "your lastName should have 2 to 20 characters")
    private String lastName;


    @Pattern(regexp = "((\\+*)((0[ -]+)*|(91 )*)(\\d{10}))", message = "enter a valid phone number")
    @NotNull(message = "contact number required")
    private String companyContact;

    @Pattern(regexp = "^[0-9]{2}[A-Z]{5}[0-9]{4}"
            + "[A-Z]{1}[1-9A-Z]{1}"
            + "Z[0-9A-Z]{1}$", message = "give in proper GST format")
    @NotNull(message = "gst number is required ")
    private String gstNumber;

    @NotNull(message = "company name is required and should be unique")
    @Size(min = 5, max = 30, message = "company name should have 5 to 30 characters")
    private String companyName;


    @NotNull(message = "pls add City name")
    @Size(min = 3, max = 20, message = "city be between 3 to 20 characters")
    private String city;

    @NotNull(message = "pls add State name")
    @Size(min = 3, max = 20, message = "state be between 3 to 20 characters")
    private String state;

    @NotNull(message = "pls add Country name")
    @Size(min = 3, max = 20, message = "country be between 3 to 20 characters")
    private String country;

    @NotNull(message = "pls add AddressLine name")
    @Size(min = 3, max = 20, message = "addressLine be between 3 to 20 characters")
    private String addressLine;

    @NotNull(message = "pls add Zipcode name")
    @Size(min = 6, message = "zipCode must be of 6 digits", max = 6)
    private String zipCode;

    @NotNull(message = "pls add Address type")
    private String label;


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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(String companyContact) {
        this.companyContact = companyContact;
    }
}
