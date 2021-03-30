package com.Bootcamp.Project.Application.dtos;

import java.util.Set;

public class SellerDTO extends UserDTO {
    private AddressDTO addressDto;
    private Long gstNumber;
    private String companyContact;
    private String companyName;
    private Set<ProductDTO> productDTOSet;

    public AddressDTO getAddressDto() {
        return addressDto;
    }

    public void setAddressDto(AddressDTO addressDto) {
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

    public Set<ProductDTO> getProductDtoSet() {
        return productDTOSet;
    }

    public void setProductDtoSet(Set<ProductDTO> productDTOSet) {
        this.productDTOSet = productDTOSet;
    }
}
