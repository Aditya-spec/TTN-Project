package com.Bootcamp.Project.Application.entities.productReview;


import com.Bootcamp.Project.Application.entities.Customer;
import com.Bootcamp.Project.Application.entities.Product;

import javax.persistence.*;

@Entity
public class ProductReview {
    @EmbeddedId
    private ProductReviewId id = new ProductReviewId();

    @ManyToOne
    @MapsId("customerId")
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @MapsId("productId")
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

    public ProductReviewId getId() {
        return id;
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

    public void setId(ProductReviewId id) { this.id = id; }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
