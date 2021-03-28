package com.Bootcamp.Project.Application.configuration;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class AppUser implements UserDetails {
    private final String email;
    private final String password;
  // private final Integer loginAttempt;
    private final Boolean isEnable;
   // private final Boolean isDeleted;
    private final List<GrantAuthorityImpl> grantAuthorities;

    /*public AppUser(String email, String password,  Boolean isEnable, Boolean isDeleted, List<GrantAuthorityImpl> grantAuthorities) {
        this.email = email;
        this.password = password;
       // this.loginAttempt = loginAttempt;
        this.isEnable = isEnable;
        //this.isDeleted = isDeleted;
        this.grantAuthorities = grantAuthorities;
    }
*/

    public AppUser(String email, String password, Boolean isEnable, List<GrantAuthorityImpl> grantAuthorities) {
        this.email = email;
        this.password = password;
        this.isEnable = isEnable;
        this.grantAuthorities = grantAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       /* System.out.println("In app user");
        grantAuthorities.forEach(authority-> System.out.println(authority.getAuthority()));
        System.out.println("Out of app user");*/
        return grantAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return (isEnable);
    }
}
