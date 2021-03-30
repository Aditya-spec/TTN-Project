package com.Bootcamp.Project.Application.dtos;

import org.springframework.stereotype.Component;

import javax.validation.constraints.*;

@Component
public class CustomerRegistrationDTO {

    @NotEmpty(message = " first name is required ")
    @Size(min = 2,max = 20,message = "size must be between 2 to 20")
    private String firstName;

    @NotNull(message = " first name is required ")
    @Size(min = 2,max = 20,message = "size must be between 2 to 20")
    private String lastName;

    @NotEmpty(message = "email is required and should be unique")
    @Email(message = "enter a valid email")
    private String email;

    @NotEmpty(message = "password is mandatory ")
    @Pattern(regexp="((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,15})",message="Password must be 8 characters long and should contain uppercase,lowercase,digit,and special character")
    private String password;

    @NotEmpty(message = "password is mandatory ")
    @Pattern(regexp="((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,15})",message="Password must be 8 characters long and should contain uppercase,lowercase,digit,and special character")
    private String confirmPassword;


    @Pattern(regexp = "((\\+*)((0[ -]+)*|(91 )*)(\\d{12}+|\\d{10}+))|\\d{5}([- ]*)\\d{6}", message = "enter a valid phone number")
    @NotNull(message = "contact number required")
    private String contact;


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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

}