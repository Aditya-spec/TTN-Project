package com.Bootcamp.Project.Application.enums;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    UNSPECIFIED_EXCEPTION("An exception has occurred", HttpStatus.INTERNAL_SERVER_ERROR), //for catching generic exceptions
    USER_NOT_FOUND("User does not exists in the database", HttpStatus.NOT_FOUND),
    ADDRESS_NOT_FOUND("Address does not exists in the database", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS("User already exists in the system", HttpStatus.FOUND),
    PASSWORD_NOT_CORRECT("password is incorrect",HttpStatus.BAD_REQUEST),
    USER_NOT_ACTIVE("User is not active",HttpStatus.UPGRADE_REQUIRED),
    ACTIVATED_TOKEN("Your account is already activated",HttpStatus.BAD_REQUEST),
    USER_IS_LOCKED("User account is locked",HttpStatus.UPGRADE_REQUIRED),
    COMPANY_ALREADY_EXISTS("Company with same name already exists in the database", HttpStatus.FOUND),
    PASSWORDS_NOT_SAME("Password and Confirm password do not match",HttpStatus.BAD_REQUEST),
    INVALID_FIELDS("Field is not present",HttpStatus.BAD_REQUEST),
    INVALID_TOKEN("Your Token is Invalid, Please enter a valid token",HttpStatus.FORBIDDEN),
    USER_IS_ADMIN("Cannot make changes, this user is Admin",HttpStatus.BAD_REQUEST);



    String errorDesc;
    HttpStatus statusCode;

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
