

package com.Bootcamp.Project.Application.entities;

import com.Bootcamp.Project.Application.enums.Label;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component
public class Address extends BaseDomain {
    private String city;
    private String state;
    private String country;
    private String addressLine;
    private int zipCode;

    @Enumerated(EnumType.STRING)
    private Label label;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //Constructors

    public Address() {
    }

    public Address(Label label) {
        this.label = label;
    }

    //Getters

    public User getUser() {
        return user;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public int getZipCode() {
        return zipCode;
    }

    public Label getLabel() {
        return label;
    }

    //Setters

    public void setUser(User user) {
        this.user = user;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public void setLabel(Label label) {
        this.label = label;
    }
}


