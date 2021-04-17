package com.Bootcamp.Project.Application.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum OrderStatusEnum {

    ORDER_PLACED(Arrays.asList()),
    CANCELLED(Arrays.asList(FromStatus.WAITING_FOR_CONFIRMATION, FromStatus.ORDER_CONFIRMED)),
    ORDER_REJECTED(Arrays.asList(FromStatus.WAITING_FOR_CONFIRMATION)),
    ORDER_CONFIRMED(Arrays.asList(FromStatus.WAITING_FOR_CONFIRMATION)),
    ORDER_SHIPPED(Arrays.asList(FromStatus.ORDER_CONFIRMED)),
    DELIVERED(Arrays.asList(FromStatus.ORDER_SHIPPED)),
    RETURN_REQUESTED(Arrays.asList()),
    RETURN_REJECTED(Arrays.asList(FromStatus.RETURN_REQUESTED)),
    RETURN_APPROVED(Arrays.asList(FromStatus.RETURN_REQUESTED)),
    PICK_UP_INITIATED(Arrays.asList(FromStatus.RETURN_APPROVED)),
    PICK_UP_COMPLETED(Arrays.asList(FromStatus.PICK_UP_INITIATED)),
    REFUND_INITIATED(Arrays.asList(FromStatus.RETURN_APPROVED)),
    REFUND_COMPLETED(Arrays.asList(FromStatus.REFUND_INITIATED)),
    CLOSED(Arrays.asList(FromStatus.CANCELLED, FromStatus.ORDER_REJECTED, FromStatus.DELIVERED, FromStatus.RETURN_REJECTED, FromStatus.REFUND_COMPLETED)),
    WAITING_FOR_CONFIRMATION(Arrays.asList(FromStatus.ORDER_PLACED));

    List<FromStatus> fromStatusList = new ArrayList<>();

    OrderStatusEnum(List<FromStatus> fromStatusList) {
        this.fromStatusList = fromStatusList;
    }

    public List<FromStatus> getFromStatusList() {
        return fromStatusList;
    }

    public void setFromStatusList(List<FromStatus> fromStatusList) {
        this.fromStatusList = fromStatusList;
    }
    /*ORDER_PLACED,
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
    WAITING_FOR_CONFIRMATION,
    CLOSED;*/
}
