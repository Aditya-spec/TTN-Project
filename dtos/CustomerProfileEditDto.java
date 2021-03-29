package com.Bootcamp.Project.Application.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CustomerProfileEditDto {

    @Email(message = "enter a valid email")
    private String email;
    @Size(min = 2, max = 20, message = "your firstName should have 2 to 20 characters")
    private String firstName;
    @Size(min = 2, max = 20, message = "your middleName should have 2 to 20 characters")
    private String middleName;
    @Size(min = 2, max = 20, message = "your lastName should have 2 to 20 characters")
    private String lastName;

    @Pattern(regexp = "((\\+*)((0[ -]+)*|(91 )*)(\\d{12}+|\\d{10}+))|\\d{5}([- ]*)\\d{6}",message = "enter a valid phone number")
    private String contactNumber;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
