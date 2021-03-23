package com.Bootcamp.Project.Application.entities.categoryMetadataFieldValues;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CMFId implements Serializable {
private int categoryMetadataFieldId;
private int categoryId;

    public int getCategoryMetadataField() {
        return categoryMetadataFieldId;
    }

    public void setCategoryMetadataField(int categoryMetadataField) {
        this.categoryMetadataFieldId = categoryMetadataField;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CMFId that = (CMFId) o;
        return categoryMetadataFieldId == that.categoryMetadataFieldId && categoryId == that.categoryId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryMetadataFieldId, categoryId);
    }
}
