package com.Bootcamp.Project.Application.entities;





import javax.persistence.*;
import java.time.LocalDateTime;
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
    private LocalDateTime resetTokenTime;



    private boolean accountNonLocked=true;
    private int failedAttempt;
    private Date lockTime;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> addressList=new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;



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

      public String getResetToken() {
        return resetToken;
    }

    public LocalDateTime getResetTokenTime() {
        return resetTokenTime;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public int getFailedAttempt() {
        return failedAttempt;
    }

    public Date getLockTime() {
        return lockTime;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public String getImagePath() {
        return imagePath;
    }

//Setters


    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setFailedAttempt(int failedAttempt) {
        this.failedAttempt = failedAttempt;
    }

    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }

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

    public void setResetTokenTime(LocalDateTime resetTokenTime) {
        this.resetTokenTime = resetTokenTime;
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


