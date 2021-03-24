package com.Bootcamp.Project.Application.entities;

import com.Bootcamp.Project.Application.entities.categoryMetadataFieldValues.CategoryMetadataFieldValues;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Category extends BaseDomain {
    private int parentId;
    private String name;

    @OneToMany(mappedBy = "category")
    private Set<CategoryMetadataFieldValues> categoryMetadataFieldValuesSet;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Product> products;

    //Constructors

    public Category() {
    }

    public Category(long id) {
        this.setId(id);
    }

   //Getters

    public String getName() {
        return name;
    }


    public int getParentId() {
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

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public void setCategoryMetadataFieldValuesSetSet(Set<CategoryMetadataFieldValues> categoryMetadataFieldValuesSetSet) {
        this.categoryMetadataFieldValuesSet = categoryMetadataFieldValuesSetSet;
    }

    public void setProduct(Product product) {
        if (product != null) {
            if (products == null) {
            products=new HashSet<>();
            }
            product.setCategory(this);
            this.getProducts().add(product);
        }
    }

    public void setCategoryMetadataFieldValues(CategoryMetadataFieldValues data){
        if(data!=null){
            if(categoryMetadataFieldValuesSet==null){
                categoryMetadataFieldValuesSet=new HashSet<>();
            }
            data.setCategory(this);
            categoryMetadataFieldValuesSet.add(data);
        }
    }


}
