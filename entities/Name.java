package com.Bootcamp.Project.Application.entities;

import org.springframework.stereotype.Component;

import javax.persistence.Embeddable;

@Embeddable
@Component
public class Name {
    private String firstName;
    private String middleName;
    private String lastName;

    //Getters

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() { return middleName; }

    public String getLastName() {
        return lastName;
    }

    //Setters

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
