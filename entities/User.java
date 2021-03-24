package com.Bootcamp.Project.Application.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User extends BaseDomain{

    @Embedded
    private Name name;
    private String email;
    private boolean active;
    private boolean deleted;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Address> addressSet;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;


    //Getters

    public String getPassword() {
        return password; }

    public Name getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public Set<Address> getAddressSet() {
        return addressSet;
    }

    public boolean getActive() {
        return active;
    }

    public Set<Role> getRoles() {
        return roles;
    }


    //Setters

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setRoles(Set<Role> roleSet) {
        if (roleSet != null) {
            if (roles == null) {
                roles = new HashSet<>();
            }
            for (Role r : roleSet) {
                roles.add(r);
            }
        }
    }

    public void setAddressSet(Set<Address> inputAddress) {


        if (inputAddress != null) {
            if (addressSet == null) {
                addressSet = new HashSet<>();
            }
            for (Address address : inputAddress) {
                address.setUser(this);
                addressSet.add(address);
            }
        }
    }


    public void setAddress(Address address) {
        if (address != null) {
            if (addressSet == null) {
                addressSet = new HashSet<>();
            }
            address.setUser(this);
            addressSet.add(address);
        }
    }
/*
    public void setRole(Role role) {
        if (role != null) {
            if (roles == null) {
                roles = new HashSet<>();
            }
            this.getRoles().add(role);
        }
    }*/

}


