package com.Bootcamp.Project.Application.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CustomerEmailDTO {

    @NotNull(message = "email cannot be empty")
    @Email(message = "give proper email format")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
