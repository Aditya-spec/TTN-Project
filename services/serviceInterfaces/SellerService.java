package com.Bootcamp.Project.Application.services.serviceInterfaces;

import com.Bootcamp.Project.Application.dtos.PasswordDTO;
import com.Bootcamp.Project.Application.dtos.SellerProfileDTO;
import com.Bootcamp.Project.Application.dtos.SellerUpdateDTO;
import com.Bootcamp.Project.Application.entities.Seller;

public interface SellerService {
    public SellerProfileDTO showProfile(String email);
    public boolean updateSeller(String email, SellerUpdateDTO sellerUpdateDto);
    public boolean checkPassword(String password, String confirmPassword);
    public boolean customerResetPassword(String email, PasswordDTO passwordDto);
}
