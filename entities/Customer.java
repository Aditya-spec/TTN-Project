package com.Bootcamp.Project.Application.entities;

import org.springframework.stereotype.Component;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Component
@PrimaryKeyJoinColumn(name = "id")
public class Customer extends User {
    private int contact;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<ProductReview> productReviewSet;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Invoice> invoiceList;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    private Set<Cart> cartSet;

    //constructors

    public Customer() {
    }

    public Customer(Long id) {
        this.setId(id);
    }

    //Getters

    public int getContact() {
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

    //Setters


    public void setProductReviewSet(List<ProductReview> productReviewSet) {
        this.productReviewSet = productReviewSet;
    }

    public void setInvoiceList(List<Invoice> invoiceList) {
        this.invoiceList = invoiceList;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }

    public void setCartSet(Set<Cart> cartSet) {
        this.cartSet = cartSet;
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

}
