package com.Bootcamp.Project.Application.dtos;

import java.util.List;
import java.util.Set;

public class CustomerDTO extends UserDTO {
    private String contact;
    private List<ProductReviewDTO> productReviewDTOList;
    private List<InvoiceDTO> invoiceDTOList;
    private Set<CartDTO> cartDtoSet;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public List<ProductReviewDTO> getProductReviewDtoList() {
        return productReviewDTOList;
    }

    public void setProductReviewDtoList(List<ProductReviewDTO> productReviewDTOList) {
        this.productReviewDTOList = productReviewDTOList;
    }

    public List<InvoiceDTO> getInvoiceDtoList() {
        return invoiceDTOList;
    }

    public void setInvoiceDtoList(List<InvoiceDTO> invoiceDTOList) {
        this.invoiceDTOList = invoiceDTOList;
    }

    public Set<CartDTO> getCartDtoSet() {
        return cartDtoSet;
    }

    public void setCartDtoSet(Set<CartDTO> cartDtoSet) {
        this.cartDtoSet = cartDtoSet;
    }
}
