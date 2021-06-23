package com.Bootcamp.Project.Application.entities.categoryMetadataFieldValues;

import com.Bootcamp.Project.Application.entities.Category;
import com.Bootcamp.Project.Application.entities.CategoryMetadataField;
import org.json.simple.JSONArray;

import javax.persistence.*;
import java.util.List;

@Entity
public class CategoryMetadataFieldValues {
    @EmbeddedId
    private CMFId id = new CMFId();

    @ManyToOne
    @MapsId(value = "categoryMetadataFieldId")
    @JoinColumn(name = "categoryMetadataField_Id")
    private CategoryMetadataField categoryMetaField;

    @ManyToOne
    @MapsId(value = "categoryId")
    @JoinColumn(name = "category_Id")
    private Category category;

    private String fieldValues;

    //Getters

    public CMFId getId() {
        return id;
    }

    public CategoryMetadataField getCategoryMetaField() {
        return categoryMetaField;
    }

    public Category getCategory() {
        return category;
    }

    public String getFieldValues() {
        return fieldValues;
    }

    //Setters

    public void setId(CMFId id) {
        this.id = id;
    }

    public void setCategoryMetaField(CategoryMetadataField categoryMetaField) {
        this.categoryMetaField = categoryMetaField; }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setFieldValues(String fieldValues) {
        this.fieldValues = fieldValues;
    }
}
