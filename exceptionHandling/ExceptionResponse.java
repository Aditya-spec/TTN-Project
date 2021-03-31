package com.Bootcamp.Project.Application.exceptionHandling;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class ExceptionResponse {

    private String errorDesc;
    private HttpStatus status;
    private Date date  ;

    public ExceptionResponse(Date date,String errorDesc, HttpStatus status) {
        this.errorDesc = errorDesc;
        this.status = status;
        this.date=date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
