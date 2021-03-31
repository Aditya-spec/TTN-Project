package com.Bootcamp.Project.Application.exceptionHandling;

import com.Bootcamp.Project.Application.enums.ErrorCode;

public class EcommerceException extends RuntimeException{
    ErrorCode errorCode;

    public EcommerceException( ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
