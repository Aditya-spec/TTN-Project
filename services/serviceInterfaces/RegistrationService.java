package com.Bootcamp.Project.Application.services.serviceInterfaces;

import com.Bootcamp.Project.Application.dtos.CustomerRegistrationDTO;
import com.Bootcamp.Project.Application.dtos.SellerRegistrationDTO;

public interface RegistrationService {
    public Boolean registerCustomer(CustomerRegistrationDTO customerRegistrationDTO);
    public Boolean activateCustomer(String token);
    public Boolean resendActivationLink(String email);
    public boolean registerSeller(SellerRegistrationDTO sellerRegistrationDTO);
}
