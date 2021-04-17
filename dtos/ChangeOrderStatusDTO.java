package com.Bootcamp.Project.Application.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ChangeOrderStatusDTO {
    @NotNull(message = "orderId cannot be null")
    private Long orderProductId;
    @NotNull(message = "fromStatus is mandatory")
    private String fromStatus;
    @NotNull(message = "toStatus is mandatory")
    private String toStatus;
    @NotNull(message = "transition comment cannot be null")
    @Size(min = 3,max = 30,message = "must have character from 3 to 30")
    private String transitionComment;

    public String getTransitionComment() {
        return transitionComment;
    }

    public void setTransitionComment(String transitionComment) {
        this.transitionComment = transitionComment;
    }

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
