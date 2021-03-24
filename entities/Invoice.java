package com.Bootcamp.Project.Application.entities;
/*
import com.Bootcamp.Project.Application.entities.orderProduct.OrderProduct;*/

import com.Bootcamp.Project.Application.entities.orderProduct.OrderProduct;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Invoice extends BaseDomain {

    private int amountPaid;

    private String paymentMethod;

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

    public Invoice(long id) {
        this.setId(id);
    }

    //Getters

    public Customer getCustomer() {
        return customer;
    }

    public int getAmountPaid() {
        return amountPaid;
    }

    public String getPaymentMethod() {
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

    public void setAmountPaid(int amountPaid) {
        this.amountPaid = amountPaid;
    }

    public void setPaymentMethod(String paymentMethod) {
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
