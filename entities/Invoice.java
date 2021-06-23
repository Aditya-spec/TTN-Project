package com.Bootcamp.Project.Application.entities;
/*
import com.Bootcamp.Project.Application.entities.OrderProduct;*/

import com.Bootcamp.Project.Application.enums.PaymentMethod;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Invoice extends BaseDomain {

    private Double amountPaid;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Embedded
    private OrderAddress orderAddress;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "invoice")
    private Set<OrderProduct> orderProductSet;

    //Constructors

    public Invoice() {
    }

    public Invoice(Long id) {
        this.setId(id);
    }

    //Getters

    public Customer getCustomer() {
        return customer;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public OrderAddress getOrderAddress() {
        return orderAddress;
    }

    public Set<OrderProduct> getOrderProductSet() {
        return orderProductSet;
    }

    //Setters

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setOrderAddress(OrderAddress orderAddress) {
        this.orderAddress = orderAddress;
    }

    public void setOrderProductSet(Set<OrderProduct> orderProductSet) {
        this.orderProductSet = orderProductSet;
    }

    public void setOrderProduct(OrderProduct orderProduct){
        if(orderProduct!=null){
            if(orderProductSet==null){
                orderProductSet=new HashSet<>();
            }
            orderProduct.setInvoice(this);
            orderProductSet.add(orderProduct);
        }
    }
}
