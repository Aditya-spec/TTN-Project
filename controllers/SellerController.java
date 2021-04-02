package com.Bootcamp.Project.Application.controllers;

import com.Bootcamp.Project.Application.dtos.*;
import com.Bootcamp.Project.Application.services.CategoryImpl;
import com.Bootcamp.Project.Application.services.SellerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    private SellerImpl sellerImpl;
    @Autowired
    CategoryImpl categoryImpl;



    @GetMapping("/home")
    public String userHome(){
        return "Seller home";
    }

    @GetMapping("/view-profile")
    public SellerProfileDTO viewProfile(HttpServletRequest request)
    {
    String email=request.getUserPrincipal().getName();
        return sellerImpl.showProfile(email);
    }

    @PatchMapping("/update-profile")
    public ResponseEntity<String> updateSeller(HttpServletRequest request, @Valid @RequestBody SellerUpdateDTO sellerUpdateDto){
        String email=request.getUserPrincipal().getName();
        if(sellerImpl.updateSeller(email,sellerUpdateDto)){
            return new ResponseEntity<>("fields updated successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("fields cannot be updated", HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<String> changePassword(HttpServletRequest request, @Valid @RequestBody PasswordDTO passwordDto){
        String email=request.getUserPrincipal().getName();
        if(sellerImpl.checkPassword(passwordDto.getPassword(), passwordDto.getConfirmPassword())){
            return new ResponseEntity<>("Password and Confirm password do not match",HttpStatus.BAD_REQUEST);
        }
        if (sellerImpl.customerResetPassword(email, passwordDto)) {
            return new ResponseEntity<>("password has been updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("password cannot be updated", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("update-address/{id}")
    public ResponseEntity<String> updatePassword(@PathVariable Long id,@Valid @RequestBody AddressUpdateDTO addressUpdateDto){
        return sellerImpl.updateAddress(id,addressUpdateDto);
    }

    @GetMapping("/view-categories")
    public List<SellerCategoryResponseDTO> showCategories(){

        return categoryImpl.showSellerCategories();
    }
}
