package com.Bootcamp.Project.Application.controllers;

import com.Bootcamp.Project.Application.dtos.*;
import com.Bootcamp.Project.Application.services.CategoryImpl;
import com.Bootcamp.Project.Application.services.ProductImpl;
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
    @Autowired
    ProductImpl productImpl;


    @GetMapping("/home")
    public String userHome() {
        return "Seller home";
    }

    @GetMapping("/view-profile")
    public SellerProfileDTO viewProfile(HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        return sellerImpl.showProfile(email);
    }

    @PatchMapping("/update-profile")
    public ResponseEntity<String> updateSeller(HttpServletRequest request, @Valid @RequestBody SellerUpdateDTO sellerUpdateDto) {
        String email = request.getUserPrincipal().getName();
        if (sellerImpl.updateSeller(email, sellerUpdateDto)) {
            return new ResponseEntity<>("fields updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("fields cannot be updated", HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<String> changePassword(HttpServletRequest request, @Valid @RequestBody PasswordDTO passwordDto) {
        String email = request.getUserPrincipal().getName();
        if (sellerImpl.checkPassword(passwordDto.getPassword(), passwordDto.getConfirmPassword())) {
            return new ResponseEntity<>("Password and Confirm password do not match", HttpStatus.BAD_REQUEST);
        }
        if (sellerImpl.customerResetPassword(email, passwordDto)) {
            return new ResponseEntity<>("password has been updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("password cannot be updated", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("update-address/{id}")
    public ResponseEntity<String> updatePassword(@PathVariable Long id, @Valid @RequestBody AddressUpdateDTO addressUpdateDto) {
        return sellerImpl.updateAddress(id, addressUpdateDto);
    }

    @GetMapping("/view-categories")
    public List<SellerCategoryResponseDTO> showCategories() {

        return categoryImpl.showSellerCategories();
    }

    @PostMapping("/add-product")
    public ResponseEntity<String> addProduct(HttpServletRequest request, @RequestBody SellerProductAddDTO sellerProductAddDTO) {
        String email = request.getUserPrincipal().getName();
        if (productImpl.addProduct(email, sellerProductAddDTO)) {
            return new ResponseEntity<>("product has been added", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("product already exists", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete-product")
    public ResponseEntity<String> deleteProduct(HttpServletRequest request, @RequestParam Long id) {
        String email = request.getUserPrincipal().getName();
        if (productImpl.deleteProduct(email, id)) {
            return new ResponseEntity<>("product deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("product doesn't exist in the database", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update-product")
    public ResponseEntity<String> updateProduct(HttpServletRequest request, @RequestBody SellerProductUpdateDTO sellerProductUpdateDTO, @RequestParam Long id) {
        String email = request.getUserPrincipal().getName();
        if (productImpl.updateProduct(email, sellerProductUpdateDTO, id)) {
            return new ResponseEntity<>("product updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Product cannot be updated", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/view-product")
    public SellerProductShowDTO getProduct(HttpServletRequest request, @RequestParam Long id) {
        String email = request.getUserPrincipal().getName();
        return productImpl.showSellerProduct(email, id);
    }

    @GetMapping("/view-products")
    public List<SellerProductShowDTO> getProducts(HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        return productImpl.showAllSellerProducts(email);
    }

    @PostMapping("/add-productVariation")
    public ResponseEntity<String> addProductVariation(HttpServletRequest request, @RequestBody ProductVariationDTO productVariationDTO) {
        String email=request.getUserPrincipal().getName();
       if(productImpl.addVariation(email,productVariationDTO)){
           return new ResponseEntity<>("product variation added successfully",HttpStatus.CREATED);
       }
        return new ResponseEntity<>("product variation cannot be added",HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PutMapping("/update-productVariation")
    public ResponseEntity<String> updateVariation(HttpServletRequest request,
                                                  @RequestBody ProductVariationUpdateDTO productVariationUpdateDTO, @RequestParam Long id){
        String email=request.getUserPrincipal().getName();
        if(productImpl.updateVariation(email,productVariationUpdateDTO,id)){
            return new ResponseEntity<>("product variation updated successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("product cannot be updated",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/view-variation")
    public ProductVariationResponseDTO viewVariation(HttpServletRequest request, @RequestParam Long id){
       String email=request.getUserPrincipal().getName();
       return productImpl.showVariation(email,id);
    }

}
