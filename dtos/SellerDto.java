package com.Bootcamp.Project.Application.dtos;

import java.util.Set;

public class SellerDto extends UserDto{
    private AddressDto addressDto;
    private Long gstNumber;
    private String companyContact;
    private String companyName;
    private Set<ProductDto> productDtoSet;

    public AddressDto getAddressDto() {
        return addressDto;
    }

    public void setAddressDto(AddressDto addressDto) {
        this.addressDto = addressDto;
    }

    public Long getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(Long gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(String companyContact) {
        this.companyContact = companyContact;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Set<ProductDto> getProductDtoSet() {
        return productDtoSet;
    }

    public void setProductDtoSet(Set<ProductDto> productDtoSet) {
        this.productDtoSet = productDtoSet;
    }
}
