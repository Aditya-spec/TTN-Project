package com.Bootcamp.Project.Application.exceptionHandling;

public class NotFoundException extends RuntimeException{
    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }
}
