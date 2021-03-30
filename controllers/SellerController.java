package com.Bootcamp.Project.Application.controllers;

import com.Bootcamp.Project.Application.dtos.AddressUpdateDto;
import com.Bootcamp.Project.Application.dtos.PasswordDto;
import com.Bootcamp.Project.Application.dtos.SellerProfileDto;
import com.Bootcamp.Project.Application.dtos.SellerUpdateDto;
import com.Bootcamp.Project.Application.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    private SellerService sellerService;


    @GetMapping("/home")
    public String userHome(){
        return "Seller home";
    }

    @GetMapping("/view-profile/{email}")
    public SellerProfileDto viewProfile(@PathVariable String email){
        return sellerService.showProfile(email);
    }

    @PatchMapping("/update-profile/{email}")
    public ResponseEntity<String> updateSeller(@PathVariable String email, @Valid @RequestBody SellerUpdateDto sellerUpdateDto){
        if(sellerService.updateSeller(email,sellerUpdateDto)){
            return new ResponseEntity<>("fields updated successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("fields cannot be updated", HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/change-password/{email}")
    public ResponseEntity<String> changePassword(@PathVariable String email, @RequestBody PasswordDto passwordDto){
        if(sellerService.checkPassword(passwordDto.getPassword(), passwordDto.getConfirmPassword())){
            return new ResponseEntity<>("Password and Confirm password do not match",HttpStatus.BAD_REQUEST);
        }
        if (sellerService.customerResetPassword(email, passwordDto)) {
            return new ResponseEntity<>("password has been updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("password cannot be updated", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("update-address/{id}")
    public ResponseEntity<String> updatePassword(@PathVariable Long id, @RequestBody AddressUpdateDto addressUpdateDto){
        return sellerService.updateAddress(id,addressUpdateDto);
    }
}
