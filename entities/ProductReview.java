package com.Bootcamp.Project.Application.entities;


import com.Bootcamp.Project.Application.entities.BaseDomain;
import com.Bootcamp.Project.Application.entities.Customer;
import com.Bootcamp.Project.Application.entities.Product;

import javax.persistence.*;

@Entity
public class ProductReview extends BaseDomain {

   @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;


    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String review;
    private int rating;

    //Getters

    public String getReview() {
        return review;
    }

    public int getRating() {
        return rating;
    }


    public Customer getCustomer() {
        return customer;
    }

    public Product getProduct() {
        return product;
    }

    //Setters

    public void setReview(String review) {
        this.review = review;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }


    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
