package com.Bootcamp.Project.Application.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product extends BaseDomain {

    private String name;
    private String description;
    private Boolean cancellable;
    private Boolean returnable;
    private String brand;
    private Boolean active=false;
   /* private String primaryImageName;*/

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductReview> productReviewSet;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductVariation> productVariations;

    //Constructors

    public Product() {
    }

    public Product(Long id) {
        this.setId(id);
    }

    //Getters

    public Seller getSeller() {
        return seller;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

   /* public String getPrimaryImageName() {
        return primaryImageName;
    }*/

    public String getDescription() {
        return description;
    }

    public Boolean getActive() {
        return active;
    }

    public Boolean getCancellable() {
        return cancellable;
    }

    public Boolean getReturnable() {
        return returnable;
    }

    public String getBrand() {
        return brand;
    }

    public List<ProductReview> getProductReviewSet() {
        return productReviewSet;
    }

    public List<ProductVariation> getProductVariations() {
        return productVariations;
    }


    //Setters

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

   /* public void setPrimaryImageName(String primaryImageName) {
        this.primaryImageName = primaryImageName;
    }*/

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCancellable(Boolean cancellable) {
        this.cancellable = cancellable;
    }

    public void setReturnable(Boolean returnable) {
        this.returnable = returnable;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setProductReviewSet(List<ProductReview> productReviewSet) {
        this.productReviewSet = productReviewSet;
    }

    public void setProductVariations(List<ProductVariation> productVariations) {
        this.productVariations = productVariations;
    }

    public void setProductReview(ProductReview productReview) {
        if (productReview != null) {
            if (productReviewSet == null) {
                productReviewSet = new ArrayList<>();
            }
            productReview.setProduct(this);
            productReviewSet.add(productReview);
        }
    }

    public void setProductVariation(ProductVariation productVariation) {
        if (productVariation != null) {
            if (productVariations == null) {
                productVariations = new ArrayList<>();
            }
            productVariation.setProduct(this);
            productVariations.add(productVariation);
        }
    }


}
