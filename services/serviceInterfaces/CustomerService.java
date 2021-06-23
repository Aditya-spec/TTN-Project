package com.Bootcamp.Project.Application.services.serviceInterfaces;

import com.Bootcamp.Project.Application.dtos.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService {
    public CustomerProfileDTO showProfile(String email);
    public List<AddressDTO> showAddresses(String email);
    public Boolean addAddress(String email, AddressDTO addressDTO);
    public boolean deleteAddress(Long id, String request);
    public boolean updateProfile(String email, CustomerProfileDTO customerProfileDto);
    public boolean checkPassword(String password, String confirmPassword);
    public boolean customerResetPassword(String email, PasswordDTO passwordDto);
    public ResponseEntity<MessageDTO> updateAddress(String email, Long id, AddressUpdateDTO addressUpdateDto);
}
