/*
package com.Bootcamp.Project.Application.dtos;

import com.Bootcamp.Project.Application.enums.Label;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CustomerUpdateDto {
    private Long id;

    @Size(min = 2, max = 20, message = "your firstName should have 2 to 20 characters")
    private String firstName;
    @Size(min = 2, max = 20, message = "your middleName should have 2 to 20 characters")
    private String middleName;
    @Size(min = 2, max = 20, message = "your lastName should have 2 to 20 characters")
    private String lastName;
    @Email
    private String email;

    private boolean active;

    private String imagePath;

    @Pattern(regexp = "((\\+*)((0[ -]+)*|(91 )*)(\\d{12}+|\\d{10}+))|\\d{5}([- ]*)\\d{6}", message = "enter a valid phone number")
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


    public void setContact(String contact) {
        this.contact = contact;
    }
}
*/
