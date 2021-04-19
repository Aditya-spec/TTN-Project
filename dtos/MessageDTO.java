package com.Bootcamp.Project.Application.dtos;


/*@Component*/
public class MessageDTO {

    private String message;
    private String time;

    public MessageDTO(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
