package com.Bootcamp.Project.Application.entities;

import com.Bootcamp.Project.Application.entities.cart.Cart;
import com.Bootcamp.Project.Application.entities.productReview.ProductReview;
import org.springframework.stereotype.Component;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Component
@PrimaryKeyJoinColumn(name = "id")
public class Customer extends User {
    private int contact;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<ProductReview> productReviewSet;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Invoice> invoiceList;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    private Set<Cart> cartSet;

    //constructors

    public Customer() {
    }

    public Customer(int id) {
        this.setId(id);
    }



    public int getContact() {
        return contact;
    }

    public Set<ProductReview> getProductReviewSet() {
        return productReviewSet;
    }

    public void setProductReviewSet(Set<ProductReview> productReviewSet) {
        this.productReviewSet = productReviewSet;
    }

    public List<Invoice> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(List<Invoice> invoiceList) {
        this.invoiceList = invoiceList;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }

    public Set<Cart> getCartSet() {
        return cartSet;
    }

    public void setCartSet(Set<Cart> cartSet) {
        this.cartSet = cartSet;
    }

    public void setProductReview(ProductReview productReview) {
        if (productReview != null) {
            if (productReviewSet == null) {
                productReviewSet = new HashSet<>();
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
