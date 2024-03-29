package com.Bootcamp.Project.Application.exceptionHandling;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@ControllerAdvice
public class BaseExceptionHandler extends ResponseEntityExceptionHandler {


    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

    @ExceptionHandler(EcommerceException.class)
    public final ResponseEntity<Object> eCommerceException(EcommerceException exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(simpleDateFormat.format(new Date()), exception.errorCode.getErrorDesc(), exception.errorCode.getStatusCode());
        return new ResponseEntity(exceptionResponse, exception.errorCode.getStatusCode());

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid
            (MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(simpleDateFormat.format(new Date()), ex.getMessage(), status);
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}


