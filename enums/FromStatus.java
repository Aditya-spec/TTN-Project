package com.Bootcamp.Project.Application.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FromStatus {
    ORDER_PLACED("Order Placed"),
    CANCELLED("Order Cancelled"),
    ORDER_REJECTED("Order Rejected"),
    ORDER_CONFIRMED("Order Confirmed"),
    ORDER_SHIPPED("Order Shipped"),
    DELIVERED("Order Delivered"),
    RETURN_REQUESTED("Return Requested"),
    RETURN_REJECTED("Return Rejected"),
    RETURN_APPROVED("Return Approved"),
    PICKUP_INITIATED("PickUp Initiated"),
    PICKUP_COMPLETED("Pickup Completed"),
    REFUND_INITIATED("Refund Initiated"),
    REFUND_COMPLETED("Refund Completed");

    private String status;

    private FromStatus(String status) {
        this.status = status;
    }

    @JsonValue
    public String getAddressType() {
        return status;
    }

}
