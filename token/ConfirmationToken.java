package com.Bootcamp.Project.Application.token;

import com.Bootcamp.Project.Application.entities.BaseDomain;
import com.Bootcamp.Project.Application.entities.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class ConfirmationToken extends BaseDomain {

    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalTime expiresAt;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User user;
    private String name;

    public ConfirmationToken() {
    }

    public ConfirmationToken(String token, LocalTime expiresAt, String name) {
        this.token = token;
        this.expiresAt = expiresAt;
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}