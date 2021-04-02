package com.Bootcamp.Project.Application.springSecurity;


import com.Bootcamp.Project.Application.entities.BaseDomain;

import javax.persistence.Entity;

@Entity
public class AuthenticatedToken extends BaseDomain {
    private String token;
    private String username;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
