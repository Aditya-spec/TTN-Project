package com.Bootcamp.Project.Application.dtos;

import javax.validation.constraints.NotNull;

public class ChangeOrderStatusDTO {
    @NotNull(message = "orderId cannot be null")
    private Long orderProductId;
    @NotNull(message = "fromStatus is mandatory")
    private String fromStatus;
    @NotNull(message = "toStatus is mandatory")
    private String toStatus;

    public Long getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(Long orderProductId) {
        this.orderProductId = orderProductId;
    }

    public String getFromStatus() {
        return fromStatus;
    }

    public void setFromStatus(String fromStatus) {
        this.fromStatus = fromStatus;
    }

    public String getToStatus() {
        return toStatus;
    }

    public void setToStatus(String toStatus) {
        this.toStatus = toStatus;
    }
}
