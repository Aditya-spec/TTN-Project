package com.Bootcamp.Project.Application.entities;

import com.Bootcamp.Project.Application.entities.categoryMetadataFieldValues.CategoryMetadataFieldValues;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
public class Category extends BaseDomain {

    private Long parentId;
    private String name;

    @OneToMany(mappedBy = "category",fetch = FetchType.EAGER)
    private Set<CategoryMetadataFieldValues> categoryMetadataFieldValuesSet;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Product> products;

    //Constructors

    public Category() {
    }

    public Category(Long id) {
        this.setId(id);
    }

    //Getters

    public String getName() {
        return name;
    }


    public Long getParentId() {
        return parentId;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Set<CategoryMetadataFieldValues> getCategoryMetadataFieldValuesSet() {
        return categoryMetadataFieldValuesSet;
    }

    //Setters

    public void setName(String name) {
        this.name = name;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public void setCategoryMetadataFieldValuesSet(Set<CategoryMetadataFieldValues> categoryMetadataFieldValuesSet) {
        this.categoryMetadataFieldValuesSet = categoryMetadataFieldValuesSet;
    }

    public void setProduct(Product product) {
        if (product != null) {
            if (products == null) {
                products = new HashSet<>();
            }
            product.setCategory(this);
            this.getProducts().add(product);
        }
    }

    public void setCategoryMetadataFieldValues(CategoryMetadataFieldValues data) {
        if (data != null) {
            if (categoryMetadataFieldValuesSet == null) {
                categoryMetadataFieldValuesSet = new HashSet<>();
            }
            data.setCategory(this);
            categoryMetadataFieldValuesSet.add(data);
        }
    }


}
