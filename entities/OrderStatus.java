package com.Bootcamp.Project.Application.entities;

import com.Bootcamp.Project.Application.enums.FromStatus;
import com.Bootcamp.Project.Application.enums.ToStatus;

import javax.persistence.*;

@Entity
public class OrderStatus extends BaseDomain {

    @OneToOne
    @JoinColumn(name = "orderProductId")
    OrderProduct orderProduct;

    private String transitionComment;

    @Enumerated(EnumType.STRING)
    private FromStatus fromStatus;

    @Enumerated(EnumType.STRING)
    private ToStatus toStatus;

    //Constructors

    public OrderStatus() {
    }

    public OrderStatus(Long id) {
        this.setId(id);
    }

    //Getters

    public OrderProduct getOrderProduct() {
        return orderProduct;
    }

    public String getTransitionComment() {
        return transitionComment;
    }

    public FromStatus getFromStatus() {
        return fromStatus;
    }

    public ToStatus getToStatus() {
        return toStatus;
    }

    //Setters

    public void setOrderProduct(OrderProduct orderProduct) {
        this.orderProduct = orderProduct;
    }

    public void setTransitionComment(String transitionComment) {
        this.transitionComment = transitionComment;
    }

    public void setFromStatus(FromStatus fromStatus) {
        this.fromStatus = fromStatus;
    }

    public void setToStatus(ToStatus toStatus) {
        this.toStatus = toStatus;
    }
}
