package com.Bootcamp.Project.Application.exceptionHandling;

import org.springframework.http.HttpStatus;

public class ExceptionResponse {

    private String errorDesc;
    private HttpStatus status;
    private String dateTime;

    public ExceptionResponse(String dateTime, String errorDesc, HttpStatus status) {
        this.errorDesc = errorDesc;
        this.status = status;
        this.dateTime = dateTime;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
