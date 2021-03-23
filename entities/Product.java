package com.Bootcamp.Project.Application.entities;

import com.Bootcamp.Project.Application.entities.productReview.ProductReview;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private Boolean isCancellable;
    private Boolean isReturnable;
    private String brand;
    private Boolean isActive;
    private String primaryImageName;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private Set<ProductReview> productReviewSet;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<ProductVariation> productVariations;

    //Constructors

    public Product() {
    }

    public Product(int id) {
        this.id = id;
    }
//Getters

    public int getId() {
        return id;
    }

    public Seller getSeller() {
        return seller;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getPrimaryImageName() {
        return primaryImageName;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getIsCancellable() {
        return isCancellable;
    }

    public Boolean getIsReturnable() {
        return isReturnable;
    }

    public String getBrand() {
        return brand;
    }

    public Boolean getIsActive(){return isReturnable;}

    public List<ProductVariation> getProductVariations() {
        return productVariations;
    }

    //Setters

    public void setId(int id) { this.id = id; }

    public void setSeller(Seller seller) { this.seller = seller; }

    public void setPrimaryImageName(String primaryImageName) {
        this.primaryImageName = primaryImageName;
    }

    public void setCategory(Category category) { this.category = category; }

    public void setName(String name) { this.name = name; }

    public void setDescription(String description) { this.description = description; }

    public void setIsCancellable(Boolean cancellable) { isCancellable = cancellable; }

    public void setIsReturnable(Boolean returnable) { isReturnable = returnable; }

    public void setBrand(String brand) { this.brand = brand; }

    public void setIsActive(Boolean active) { isActive = active; }

    public void setProductVariations(List<ProductVariation> productVariations) {
        this.productVariations = productVariations;
    }

    public void setProductReview(ProductReview productReview){
        if(productReview!=null){
            if(productReviewSet==null){
                productReviewSet=new HashSet<>();
            }
            productReview.setProduct(this);
            productReviewSet.add(productReview);
        }
    }

    public void setProductVariation(ProductVariation productVariation){
        if(productVariation!=null){
            if(productVariations==null){
                productVariations=new ArrayList<>();
            }
            productVariation.setProduct(this);
            productVariations.add(productVariation);
        }
    }


}
