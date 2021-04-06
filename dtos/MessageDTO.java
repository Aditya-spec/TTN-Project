package com.Bootcamp.Project.Application.dtos;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.stereotype.Component;

@Component
public class MessageDTO {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
