package com.Bootcamp.Project.Application.services.serviceInterfaces;

import com.Bootcamp.Project.Application.dtos.AddressDTO;
import com.Bootcamp.Project.Application.dtos.AddressUpdateDTO;
import com.Bootcamp.Project.Application.dtos.CustomerProfileDTO;
import com.Bootcamp.Project.Application.dtos.PasswordDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService {
    public CustomerProfileDTO showProfile(String email);
    public List<AddressDTO> showAddresses(String email);
    public Boolean addAddress(String email, AddressDTO addressDTO);
    public boolean deleteAddress(Long id);
    public boolean updateProfile(String email, CustomerProfileDTO customerProfileDto);
    public boolean checkPassword(String password, String confirmPassword);
    public boolean customerResetPassword(String email, PasswordDTO passwordDto);
    public ResponseEntity<String> updateAddress(Long id, AddressUpdateDTO addressUpdateDto);
}
