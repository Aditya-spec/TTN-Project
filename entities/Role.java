package com.Bootcamp.Project.Application.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String authorization;
    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    //Constructors
    public Role() {
    }

    public Role(String authorization) {
        this.authorization = authorization;
    }

    //Getters

    public long getId() {
        return id;
    }

    public String getAuthorization() {
        return authorization;
    }

    public List<User> getUsers() {
        return users;
    }

    //Setters

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }
}
