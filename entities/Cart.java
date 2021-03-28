package com.Bootcamp.Project.Application.entities;

import com.Bootcamp.Project.Application.entities.BaseDomain;
import com.Bootcamp.Project.Application.entities.Customer;
import com.Bootcamp.Project.Application.entities.ProductVariation;

import javax.persistence.*;

@Entity
public class Cart extends BaseDomain {

   @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "product_variation_id")
    private ProductVariation productVariation;

    private Boolean wishListItem=false;
    private int quantity;

    //Getters

    public Customer getCustomer() {
        return customer;
    }

    public ProductVariation getProductVariation() {
        return productVariation;
    }

    public Boolean getWishListItem() {
        return wishListItem;
    }

    public int getQuantity() {
        return quantity;
    }

    //Setters

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setProductVariation(ProductVariation productVariation) {
        this.productVariation = productVariation;
    }

    public void setWishListItem(Boolean wishListItem) {
        this.wishListItem = wishListItem;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
