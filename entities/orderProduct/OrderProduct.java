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
    @JoinColumn(name = "variation_id")
    private ProductVariation productVariation;

    private int quantity;
    private int price;

    public OrderProductId getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(OrderProductId orderProductId) {
        this.orderProductId = orderProductId;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public ProductVariation getProductVariation() {
        return productVariation;
    }

    public void setProductVariation(ProductVariation productVariation) {
        this.productVariation = productVariation;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
