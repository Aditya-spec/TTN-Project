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

    @NotNull(message = "city name is required")
    @Size(min = 3,max = 20,message = "must be between 3 to 20 characters")
    private String city;
    @NotNull(message = "state name is required")
    @Size(min = 3,max = 20,message = "must be between 3 to 20 characters")
    private String state;
    @NotNull(message = "country name is required")
    @Size(min = 3,max = 20,message = "must be between 3 to 20 characters")
    private String country;
    @NotNull(message = "addressLine  is required")
    @Size(min = 3,max = 20,message = "must be between 3 to 20 characters")
    private String addressLine;
    @NotNull(message = "zipCode  is required")
    @Size(min = 3,max = 20,message = "must be between 3 to 20 characters")
    private int zipCode;

    @NotEmpty(message = " first name is required ")
    @Size(min = 2,max = 20,message = "must be between 2 to 20 characters")
    private String firstName;

    @NotNull(message = " first name is required ")
    @Size(min = 2,max = 20,message = "must be between 2 to 20 characters")
    private String lastName;



    @Pattern(regexp = "((\\+*)((0[ -]+)*|(91 )*)(\\d{12}+|\\d{10}+))|\\d{5}([- ]*)\\d{6}", message = "enter a valid phone number")
    @NotNull(message = "contact number required")
    private String companyContact;

    @Pattern(regexp = "^[0-9]{2}[A-Z]{5}[0-9]{4}"
            + "[A-Z]{1}[1-9A-Z]{1}"
                    + "Z[0-9A-Z]{1}$",message = "give in proper GST format")
    @NotNull(message = "gst number is required ")
    private String gstNumber;

    @NotNull(message = "company name is required and should be unique")
    private String companyName;

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
