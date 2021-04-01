package com.Bootcamp.Project.Application.entities;

import com.Bootcamp.Project.Application.entities.categoryMetadataFieldValues.CategoryMetadataFieldValues;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class CategoryMetadataField extends BaseDomain {
    @Column(unique = true)
    private String name;
    @OneToMany(mappedBy = "categoryMetaField")
    private Set<CategoryMetadataFieldValues> categoryMetadataFieldValuesSet=new HashSet<>();

    //Constructors

    public CategoryMetadataField() {
    }

    public CategoryMetadataField(Long id) {
        this.setId(id);
    }

    //Getters

    public String getName() {
        return name;
    }

    public Set<CategoryMetadataFieldValues> getCategoryMetadataFieldValuesSet() {
        return categoryMetadataFieldValuesSet;
    }

    //Setters

    public void setName(String name) {
        this.name = name;
    }

    public void setCategoryMetadataFieldValuesSet(Set<CategoryMetadataFieldValues> categoryMetadataFieldValuesSet) {
        this.categoryMetadataFieldValuesSet = categoryMetadataFieldValuesSet;
    }

    public void setCategoryMetadataFieldValues(CategoryMetadataFieldValues data) {
        if (data != null) {
            if (categoryMetadataFieldValuesSet == null) {
                categoryMetadataFieldValuesSet = new HashSet<>();
            }
            data.setCategoryMetaField(this);
            categoryMetadataFieldValuesSet.add(data);
        }
    }
}
