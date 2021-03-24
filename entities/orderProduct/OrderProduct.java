package com.Bootcamp.Project.Application.entities.orderProduct;

import com.Bootcamp.Project.Application.entities.Invoice;
import com.Bootcamp.Project.Application.entities.ProductVariation;

import javax.persistence.*;

@Entity
public class OrderProduct {
    @EmbeddedId
    private OrderProductId orderProductId=new OrderProductId();

    @ManyToOne
    @MapsId(value = "invoiceId")
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @ManyToOne
    @MapsId(value = "productVariationId" )
    @JoinColumn(name = "product_variation_id")
    private ProductVariation productVariation;

    private int quantity;
    private int price;

    //Getters

    public OrderProductId getOrderProductId() {
        return orderProductId;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public ProductVariation getProductVariation() {
        return productVariation;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    //Setters

    public void setOrderProductId(OrderProductId orderProductId) {
        this.orderProductId = orderProductId;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public void setProductVariation(ProductVariation productVariation) {
        this.productVariation = productVariation;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
