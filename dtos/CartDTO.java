package com.Bootcamp.Project.Application.dtos;

public class CartDTO extends BaseDomainDTO {
    private CustomerDTO customerDto;
    private ProductVariationDTO productVariationDto;

    private Boolean wishListItem;
    private int quantity;

    public CustomerDTO getCustomerDto() {
        return customerDto;
    }

    public void setCustomerDto(CustomerDTO customerDto) {
        this.customerDto = customerDto;
    }

    public ProductVariationDTO getProductVariationDto() {
        return productVariationDto;
    }

    public void setProductVariationDto(ProductVariationDTO productVariationDto) {
        this.productVariationDto = productVariationDto;
    }

    public Boolean getWishListItem() {
        return wishListItem;
    }

    public void setWishListItem(Boolean wishListItem) {
        this.wishListItem = wishListItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
