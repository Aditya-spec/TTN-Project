package com.Bootcamp.Project.Application.controllers;

import com.Bootcamp.Project.Application.dtos.AddressUpdateDTO;
import com.Bootcamp.Project.Application.dtos.CustomerProfileDTO;
import com.Bootcamp.Project.Application.dtos.PasswordDTO;
import com.Bootcamp.Project.Application.dtos.ShowAddressDTO;
import com.Bootcamp.Project.Application.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping("/home")
    public String indexPremium() {
        return "Customer Home";
    }

    @GetMapping("/view-profile/{email}")
    public CustomerProfileDTO showProfile(@PathVariable String email) {
        return customerService.showProfile(email);
    }

    @GetMapping("/view-addresses/{email}")
    public List<ShowAddressDTO> showAddressList(@PathVariable String email) {
        return customerService.showAddresses(email);
    }

    @PostMapping("/add-address/{email}")
    public ResponseEntity<String> addAddress(@PathVariable String email, @Valid @RequestBody ShowAddressDTO showAddressDto) {
        if (customerService.addAddress(email, showAddressDto)) {
            return new ResponseEntity<>("address added successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("address can't be updated", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete-address/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long id) {
        if (customerService.deleteAddress(id)) {
            return new ResponseEntity<>("address deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("address couldn't be deleted", HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/update-profile/{email}")
    public ResponseEntity<String> updateCustomer(@PathVariable String email, @Valid @RequestBody CustomerProfileDTO customerProfileDto){
       if(customerService.updateProfile(email,  customerProfileDto)) {
           return new ResponseEntity<>("fields updated successfully",HttpStatus.OK);
       }
       return new ResponseEntity<>("fields cannot be updated",HttpStatus.BAD_REQUEST);
   }

    @PatchMapping("/change-password/{email}")
    public ResponseEntity<String> changePassword(@PathVariable String email, @RequestBody PasswordDTO passwordDto){
        if(customerService.checkPassword(passwordDto.getPassword(), passwordDto.getConfirmPassword())){
            return new ResponseEntity<>("Password and Confirm password do not match",HttpStatus.BAD_REQUEST);
        }
        if (customerService.customerResetPassword(email, passwordDto)) {
            return new ResponseEntity<>("password has been updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("password cannot be updated", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("update-address/{id}")
    public ResponseEntity<String> updatePassword(@PathVariable Long id, @Valid @RequestBody AddressUpdateDTO addressUpdateDto){
        return customerService.updateAddress(id,addressUpdateDto);
    }
}
