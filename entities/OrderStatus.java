package com.Bootcamp.Project.Application.entities;


import com.Bootcamp.Project.Application.enums.OrderStatusEnum;


import javax.persistence.*;

@Entity
public class OrderStatus extends BaseDomain {

    @OneToOne
    @JoinColumn(name = "orderProductId")
    OrderProduct orderProduct;

    private String transitionComment;

    @Enumerated(EnumType.STRING)
    private OrderStatusEnum fromStatus;

    @Enumerated(EnumType.STRING)
    private OrderStatusEnum toStatus;

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

    public OrderStatusEnum getFromStatus() {
        return fromStatus;
    }

    public OrderStatusEnum getToStatus() {
        return toStatus;
    }

    //Setters

    public void setOrderProduct(OrderProduct orderProduct) {
        this.orderProduct = orderProduct;
    }

    public void setTransitionComment(String transitionComment) {
        this.transitionComment = transitionComment;
    }

    public void setFromStatus(OrderStatusEnum fromStatus) {
        this.fromStatus = fromStatus;
    }

    public void setToStatus(OrderStatusEnum toStatus) {
        this.toStatus = toStatus;
    }
}
