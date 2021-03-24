package com.Bootcamp.Project.Application.entities.categoryMetadataFieldValues;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CMFId implements Serializable {
private long categoryMetadataFieldId;
private long categoryId;

    //Getters

    public long getCategoryMetadataField() {
        return categoryMetadataFieldId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    //Setters

    public void setCategoryMetadataField(long categoryMetadataField) {
        this.categoryMetadataFieldId = categoryMetadataField;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CMFId cmfId = (CMFId) o;
        return categoryMetadataFieldId == cmfId.categoryMetadataFieldId && categoryId == cmfId.categoryId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryMetadataFieldId, categoryId);
    }
}
