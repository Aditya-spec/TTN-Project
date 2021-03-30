package com.Bootcamp.Project.Application.entities;

/*
import com.Bootcamp.Project.Application.passwordValidation.ValidPassword;
*/
/*
import com.Bootcamp.Project.Application.passwordValidation.ValidPassword;*/
/*
import com.Bootcamp.Project.Application.token.ConfirmationToken;*/

import com.Bootcamp.Project.Application.token.ConfirmationToken;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalTime;
import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends BaseDomain{

    private Name name;
    private String email;
    private boolean active;
    private String imagePath;



    private String password;
    private String resetToken;
    private LocalTime resetTokenTime;
    private int loginAttempt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> addressList=new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<ConfirmationToken> confirmationTokenList;

    //Getters

    public String getPassword() {
        return password; }

    public Name getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public boolean getActive() {
        return active;
    }

    public List<ConfirmationToken> getConfirmationTokenList() {
        return confirmationTokenList;
    }

    public String getResetToken() {
        return resetToken;
    }

    public LocalTime getResetTokenTime() {
        return resetTokenTime;
    }

    public int getLoginAttempt() {
        return loginAttempt;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public String getImagePath() {
        return imagePath;
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

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public void setResetTokenTime(LocalTime resetTokenTime) {
        this.resetTokenTime = resetTokenTime;
    }

    public void setLoginAttempt(int loginAttempt) {
        this.loginAttempt = loginAttempt;
    }

    public void setConfirmationTokenList(List<ConfirmationToken> confirmationTokenList) {
        this.confirmationTokenList = confirmationTokenList;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setRoles(List<Role> roleList) {
        if (roleList != null) {
            if (roles == null) {
                roles = new ArrayList<>();
            }
            for (Role r : roleList) {
                roles.add(r);
            }
        }
    }

    public void setAddressList(List<Address> inputAddress) {

        if (inputAddress != null) {
            if (addressList == null) {
                addressList = new ArrayList<>();
            }
            for (Address address : inputAddress) {
                address.setUser(this);
                addressList.add(address);
            }
        }
    }


    public void setAddress(Address address) {
        if (address != null) {
            if (addressList == null) {
                addressList = new ArrayList<>();
            }
            address.setUser(this);
            addressList.add(address);
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

    //toString overridden
}


