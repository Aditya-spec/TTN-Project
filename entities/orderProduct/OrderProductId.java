package com.Bootcamp.Project.Application.entities.orderProduct;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderProductId implements Serializable {
    private long invoiceId;
    private long productVariationId;

    //Getters

    public long getInvoiceId() {
        return invoiceId;
    }

    public long getProductVariationId() {
        return productVariationId;
    }

    //Setters

    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public void setProductVariationId(long productVariationId) {
        this.productVariationId = productVariationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderProductId that = (OrderProductId) o;
        return invoiceId == that.invoiceId && productVariationId == that.productVariationId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, productVariationId);
    }
}
