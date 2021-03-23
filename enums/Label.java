package com.Bootcamp.Project.Application.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Label {
    HOME("Home"),
    OFFICE("Office");

    private String addressType;

    private Label(String addressType) {
        this.addressType = addressType;
    }

    @JsonValue
    public String getAddressType() {
        return addressType;
    }
}
