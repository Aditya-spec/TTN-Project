package com.Bootcamp.Project.Application.controllers;

import com.Bootcamp.Project.Application.dtos.AddressUpdateDto;
import com.Bootcamp.Project.Application.dtos.CustomerProfileDto;
import com.Bootcamp.Project.Application.dtos.PasswordDto;
import com.Bootcamp.Project.Application.dtos.ShowAddressDto;
import com.Bootcamp.Project.Application.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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
    public CustomerProfileDto showProfile(@PathVariable String email) {
        return customerService.showProfile(email);
    }

    @GetMapping("/view-addresses/{email}")
    public List<ShowAddressDto> showAddressList(@PathVariable String email) {
        return customerService.showAddresses(email);
    }

    @PostMapping("/add-address/{email}")
    public ResponseEntity<String> addAddress(@PathVariable String email, @Valid @RequestBody ShowAddressDto showAddressDto) {
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

   /* @PatchMapping("/update-profile/{email}")
    public ResponseEntity<String> updateCustomer(@PathVariable String email, @Valid @RequestBody Map<Object, Object> fields){
       if(customerService.updateProfile(email,fields)) {
           return new ResponseEntity<>("fields updated successfully",HttpStatus.OK);
       }
       return new ResponseEntity<>("fields cannot be updated",HttpStatus.BAD_REQUEST);
    }*/
    @PatchMapping("/update-profile/{email}")
    public ResponseEntity<String> updateCustomer(@PathVariable String email, @Valid @RequestBody CustomerProfileDto customerProfileDto){
       if(customerService.updateProfile(email,  customerProfileDto)) {
           return new ResponseEntity<>("fields updated successfully",HttpStatus.OK);
       }
       return new ResponseEntity<>("fields cannot be updated",HttpStatus.BAD_REQUEST);
   }

    @PatchMapping("/change-password/{email}")
    public ResponseEntity<String> changePassword(@PathVariable String email, @RequestBody PasswordDto passwordDto){
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
    public ResponseEntity<String> updatePassword(@PathVariable Long id, @Valid @RequestBody AddressUpdateDto addressUpdateDto){
        return customerService.updateAddress(id,addressUpdateDto);
    }
}
