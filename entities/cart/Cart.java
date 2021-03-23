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

    private Boolean isWishListItem;
    private int quantity;

    public CartId getCartId() {
        return cartId;
    }

    public void setCartId(CartId cartId) {
        this.cartId = cartId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ProductVariation getProductVariation() {
        return productVariation;
    }

    public void setProductVariation(ProductVariation productVariation) {
        this.productVariation = productVariation;
    }

    public Boolean getWishListItem() {
        return isWishListItem;
    }

    public void setWishListItem(Boolean wishListItem) {
        isWishListItem = wishListItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
