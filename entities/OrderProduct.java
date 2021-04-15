package com.Bootcamp.Project.Application.entities;

import com.Bootcamp.Project.Application.entities.BaseDomain;
import com.Bootcamp.Project.Application.entities.Invoice;
/*import com.Bootcamp.Project.Application.entities.OrderStatus;*/
import com.Bootcamp.Project.Application.entities.ProductVariation;

import javax.persistence.*;

@Entity
public class OrderProduct extends BaseDomain {

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "product_variation_id")
    private ProductVariation productVariation;

    @OneToOne(mappedBy = "orderProduct",cascade = CascadeType.ALL)
    OrderStatus orderStatus;


    private int quantity;
    private Double price;

    //Constructors

    public OrderProduct() {
    }

    public OrderProduct(Long id) {
        this.setId(id);
    }


    //Getters


    public Invoice getInvoice() {
        return invoice;
    }

    public ProductVariation getProductVariation() {
        return productVariation;
    }

    public int getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    //Setters



    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public void setProductVariation(ProductVariation productVariation) {
        this.productVariation = productVariation;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
