package com.Bootcamp.Project.Application.dtos;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Component
public class NameDTO {

    @NotEmpty(message = "first name is required")
    private String firstName;

    private String middleName;

    @NotEmpty(message = "last name is required")
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleNAme() {
        return middleName;
    }

    public void setMiddleNAme(String middleNAme) {
        this.middleName = middleNAme;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "NameDTO{" +
                "firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
