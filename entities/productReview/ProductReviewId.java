package com.Bootcamp.Project.Application.entities.productReview;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductReviewId implements Serializable {

    private long customerId;
    private long productId;

    //Getters

    public long getCustomerId() {
        return customerId;
    }

    public long getProductId() {
        return productId;
    }

    //Setters

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductReviewId that = (ProductReviewId) o;
        return customerId == that.customerId && productId == that.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, productId);
    }
}
