package com.Bootcamp.Project.Application.entities;

import org.springframework.stereotype.Component;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Entity
@Component
@PrimaryKeyJoinColumn(name = "id")
public class Customer extends User {


    private String contact;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<ProductReview> productReviewSet;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Invoice> invoiceList;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    private Set<Cart> cartSet;

    private String activationToken;

    private LocalDateTime expiresAt;


    //constructors

    public Customer() {
    }

    public Customer(Long id) {
        this.setId(id);
    }

    //Getters

    public String getContact() {
        return contact;
    }

    public List<ProductReview> getProductReviewSet() {
        return productReviewSet;
    }

    public List<Invoice> getInvoiceList() {
        return invoiceList;
    }

    public Set<Cart> getCartSet() {
        return cartSet;
    }

    public String getActivationToken() {
        return activationToken;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    //Setters


    public void setProductReviewSet(List<ProductReview> productReviewSet) {
        this.productReviewSet = productReviewSet;
    }

    public void setInvoiceList(List<Invoice> invoiceList) {
        this.invoiceList = invoiceList;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setCartSet(Set<Cart> cartSet) {
        this.cartSet = cartSet;
    }

    public void setActivationToken(String activationToken) {
        this.activationToken = activationToken;
    }

    public void setProductReview(ProductReview productReview) {
        if (productReview != null) {
            if (productReviewSet == null) {
                productReviewSet = new ArrayList<>();
            }
            productReview.setCustomer(this);
            productReviewSet.add(productReview);
        }
    }
    public void setCart(Cart cart){
        if(cart !=null){
            if(cartSet==null){
                cartSet=new HashSet<>();
            }
            cart.setCustomer(this);
            this.getCartSet().add(cart);
        }
    }



    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "contact=" + contact +
                ", productReviewSet=" + productReviewSet +
                ", invoiceList=" + invoiceList +
                ", cartSet=" + cartSet +
                '}';
    }
}
/*
@NotEmpty(message = "Password is a mandatory field")
@Length(min = 8, max = 15, message = "The Length of the password should be between 8 to 15 characters.")
@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d.*)(?=.*\\W.*)[a-zA-Z0-9\\S]{8,15}$",
        message = "The Password should be 8-15 Characters with atleast 1 Lower case, 1 Upper case, 1 Special Character, 1 Number")
*/

