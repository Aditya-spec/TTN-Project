package com.Bootcamp.Project.Application.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ToStatus {

    CANCELLED,
    ORDER_REJECTED,
    ORDER_CONFIRMED,
    ORDER_SHIPPED,
    DELIVERED,
    RETURN_REQUESTED,
    RETURN_REJECTED,
    RETURN_APPROVED,
    PICKUP_INITIATED,
    PICKUP_COMPLETED,
    REFUND_INITIATED,
    REFUND_COMPLETED,
    CLOSED
}
