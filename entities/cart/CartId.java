package com.Bootcamp.Project.Application.entities.cart;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CartId implements Serializable {
private long customerId;
private long productVariationId;

    //Getters

    public long getCustomerId() {
        return customerId;
    }

    public long getProductVariationId() {
        return productVariationId;
    }

    //Setters

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public void setProductVariationId(long productVariationId) {
        this.productVariationId = productVariationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartId cartId = (CartId) o;
        return customerId == cartId.customerId && productVariationId == cartId.productVariationId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, productVariationId);
    }
}
