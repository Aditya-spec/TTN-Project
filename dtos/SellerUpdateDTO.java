package com.Bootcamp.Project.Application.dtos;


import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SellerUpdateDTO {
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(String companyContact) {
        this.companyContact = companyContact;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
