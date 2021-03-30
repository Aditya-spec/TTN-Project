package com.Bootcamp.Project.Application.exceptionHandling;

public class PasswordNotMatch extends RuntimeException{
    public PasswordNotMatch() {
        super();
    }

    public PasswordNotMatch(String message) {
        super(message);
    }
}
