package com.Bootcamp.Project.Application.enums;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    UNSPECIFIED_EXCEPTION("An exception has occurred", HttpStatus.INTERNAL_SERVER_ERROR,403), //for catching generic exceptions
    USER_NOT_FOUND("User does not exists in the database", HttpStatus.NOT_FOUND,404),
    ADDRESS_NOT_FOUND("Address does not exists in the database", HttpStatus.NOT_FOUND,404),
    USER_ALREADY_EXISTS("User already exists in the system", HttpStatus.FOUND,302),
    PASSWORD_NOT_CORRECT("password is incorrect",HttpStatus.BAD_REQUEST,400),
    USER_NOT_ACTIVE("User is not active",HttpStatus.UPGRADE_REQUIRED,426),
    ACTIVATED_TOKEN("Your account is already activated",HttpStatus.BAD_REQUEST,400),
    USER_IS_LOCKED("User account is locked",HttpStatus.UPGRADE_REQUIRED,426),
    COMPANY_ALREADY_EXISTS("Company with same name already exists in the database", HttpStatus.FOUND,302),
    PASSWORDS_NOT_SAME("Password and Confirm password do not match",HttpStatus.BAD_REQUEST,400),
    INVALID_FIELDS("Field is not present",HttpStatus.BAD_REQUEST,400),
    INVALID_TOKEN("Your Token is Invalid, Please enter a valid token",HttpStatus.FORBIDDEN,403),
    ALREADY_EXISTS("given data already exists in the system",HttpStatus.BAD_REQUEST,400),
    PARENT_CATEGORY_NOT_EXISTS("Parent category for given category do not exists",HttpStatus.BAD_REQUEST,400),
    NO_DATA("No data to show",HttpStatus.NOT_FOUND,404),
    NOT_FOUND("given data does not found in the system",HttpStatus.BAD_REQUEST,400),
    USER_IS_ADMIN("Cannot make changes, this user is Admin",HttpStatus.BAD_REQUEST,400);



    String errorDesc;
    HttpStatus statusCode;
    int code;

    ErrorCode(String errorDesc, HttpStatus status, int code) {
        this.errorDesc = errorDesc;
        this.statusCode = status;
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    ErrorCode(String errorDesc, HttpStatus status) {
        this.errorDesc = errorDesc;
        this.statusCode = status;
    }

    public String getErrorDesc() {
        return errorDesc;
    }
    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
