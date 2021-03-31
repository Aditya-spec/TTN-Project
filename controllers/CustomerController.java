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

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("/view-profile")
    public CustomerProfileDTO showProfile(HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        return customerService.showProfile(email);
    }

    @GetMapping("/view-addresses")
    public List<ShowAddressDTO> showAddressList(HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        return customerService.showAddresses(email);
    }

    @PostMapping("/add-address")
    public ResponseEntity<String> addAddress(HttpServletRequest request, @Valid @RequestBody ShowAddressDTO showAddressDto) {
        String email = request.getUserPrincipal().getName();
        if (customerService.addAddress(email, showAddressDto)) {
            return new ResponseEntity<>("address added successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("address can't be updated", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete-address")
    public ResponseEntity<String> deleteAddress(@RequestParam Long id) {
        if (customerService.deleteAddress(id)) {
            return new ResponseEntity<>("address deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("address couldn't be deleted", HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/update-profile")
    public ResponseEntity<String> updateCustomer(HttpServletRequest request, @Valid @RequestBody CustomerProfileDTO customerProfileDto) {
        String email=request.getUserPrincipal().getName();
        if (customerService.updateProfile(email, customerProfileDto)) {
            return new ResponseEntity<>("fields updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("fields cannot be updated", HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<String> changePassword(HttpServletRequest request, @RequestBody PasswordDTO passwordDto) {
        String email=request.getUserPrincipal().getName();
        if (customerService.checkPassword(passwordDto.getPassword(), passwordDto.getConfirmPassword())) {
            return new ResponseEntity<>("Password and Confirm password do not match", HttpStatus.BAD_REQUEST);
        }
        if (customerService.customerResetPassword(email, passwordDto)) {
            return new ResponseEntity<>("password has been updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("password cannot be updated", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("update-address")
    public ResponseEntity<String> updatePassword(@RequestParam Long id,HttpServletRequest request, @Valid @RequestBody AddressUpdateDTO addressUpdateDto) {
        return customerService.updateAddress(id, addressUpdateDto);
    }
}
