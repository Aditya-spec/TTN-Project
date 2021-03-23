package com.Bootcamp.Project.Application.entities;
/*
import com.Bootcamp.Project.Application.entities.orderProduct.OrderProduct;*/

import com.Bootcamp.Project.Application.entities.orderProduct.OrderProduct;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int amountPaid;
/*
    @Temporal(value = TemporalType.DATE)
    private Date dateCreated;*/

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

    public Invoice(int id) {
        this.id = id;
    }


    //Getters

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getAmountPaid() {
        return amountPaid;
    }

    /*public Date getDateCreated() {
        return dateCreated;
    }
*/
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

    public void setId(int id) { this.id = id; }

    public void setAmountPaid(int amountPaid) {
        this.amountPaid = amountPaid;
    }

  /*  public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }*/

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
