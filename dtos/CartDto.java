package com.Bootcamp.Project.Application.dtos;

public class CartDto extends BaseDomainDto {
    private CustomerDto customerDto;
    private ProductVariationDto productVariationDto;

    private Boolean wishListItem;
    private int quantity;

    public CustomerDto getCustomerDto() {
        return customerDto;
    }

    public void setCustomerDto(CustomerDto customerDto) {
        this.customerDto = customerDto;
    }

    public ProductVariationDto getProductVariationDto() {
        return productVariationDto;
    }

    public void setProductVariationDto(ProductVariationDto productVariationDto) {
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
