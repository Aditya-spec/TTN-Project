package com.Bootcamp.Project.Application.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class PasswordTokenDTO {

    @NotEmpty(message = "password is mandatory ")
    @Pattern(regexp="((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,15})",message="Password must be 8 characters long and should contain uppercase,lowercase,digit,and special character")
    private String password;
    @NotEmpty(message = "confirmPassword is mandatory ")
    @Pattern(regexp="((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,15})",message="Password must be 8 characters long and should contain uppercase,lowercase,digit,and special character")
    private String confirmPassword;

    @NotEmpty(message = "token cannot be empty")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
