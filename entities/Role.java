package com.Bootcamp.Project.Application.entities;
/*
import com.Bootcamp.Project.Application.enums.RoleAuthorized;*/

import javax.persistence.*;
import java.util.List;

@Entity
public class Role extends BaseDomain{

    private String authorization;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    //Constructors

    public Role() {
    }

    public Role(String authorization) {
        this.authorization = authorization;
    }


    public List<User> getUsers() {
        return users;
    }

    public String getAuthorization() {
        return authorization;
    }

    //Setters

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    //toString



}
