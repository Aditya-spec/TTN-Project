package com.Bootcamp.Project.Application.entities.cart;

import com.Bootcamp.Project.Application.entities.Customer;
import com.Bootcamp.Project.Application.entities.ProductVariation;

import javax.persistence.*;

@Entity
public class Cart {
    @EmbeddedId
    private CartId cartId = new CartId();

    @ManyToOne
    @MapsId("customerId")
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @MapsId(value = "productVariationId" )
    @JoinColumn(name = "product_variation_id")
    private ProductVariation productVariation;

    private Boolean wishListItem;
    private int quantity;

    //Getters

    public CartId getCartId() {
        return cartId;
    }

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

    public void setCartId(CartId cartId) {
        this.cartId = cartId;
    }

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
