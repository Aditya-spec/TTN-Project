package com.Bootcamp.Project.Application.dtos;


public class MessageDTO {

    private String message;
    private String dateTime;

    public MessageDTO(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
