package com.Bootcamp.Project.Application.dtos;

import java.util.List;
import java.util.Set;

public class CustomerDto extends UserDto{
    private String contact;
    private List<ProductReviewDto> productReviewDtoList;
    private List<InvoiceDto> invoiceDtoList;
    private Set<CartDto> cartDtoSet;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public List<ProductReviewDto> getProductReviewDtoList() {
        return productReviewDtoList;
    }

    public void setProductReviewDtoList(List<ProductReviewDto> productReviewDtoList) {
        this.productReviewDtoList = productReviewDtoList;
    }

    public List<InvoiceDto> getInvoiceDtoList() {
        return invoiceDtoList;
    }

    public void setInvoiceDtoList(List<InvoiceDto> invoiceDtoList) {
        this.invoiceDtoList = invoiceDtoList;
    }

    public Set<CartDto> getCartDtoSet() {
        return cartDtoSet;
    }

    public void setCartDtoSet(Set<CartDto> cartDtoSet) {
        this.cartDtoSet = cartDtoSet;
    }
}
