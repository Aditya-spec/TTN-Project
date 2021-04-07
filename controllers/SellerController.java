package com.Bootcamp.Project.Application.controllers;

import com.Bootcamp.Project.Application.dtos.*;
import com.Bootcamp.Project.Application.services.CategoryImpl;
import com.Bootcamp.Project.Application.services.ProductImpl;
import com.Bootcamp.Project.Application.services.SellerImpl;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
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
    @Autowired
    MessageDTO messageDTO;


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
    public ResponseEntity<MessageDTO> updateSeller(HttpServletRequest request, @Valid @RequestBody SellerUpdateDTO sellerUpdateDto) {
        String email = request.getUserPrincipal().getName();
        if (sellerImpl.updateSeller(email, sellerUpdateDto)) {
            messageDTO.setMessage("fields updated successfully");
            return new ResponseEntity<>(messageDTO, HttpStatus.OK);
        }
        messageDTO.setMessage("fields cannot be updated");
        return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<MessageDTO> changePassword(HttpServletRequest request, @Valid @RequestBody PasswordDTO passwordDto) {
        String email = request.getUserPrincipal().getName();
        if (sellerImpl.checkPassword(passwordDto.getPassword(), passwordDto.getConfirmPassword())) {
            messageDTO.setMessage("Password and Confirm password do not match");
            return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
        }
        if (sellerImpl.sellerResetPassword(email, passwordDto)) {
            messageDTO.setMessage("password has been updated successfully");
            return new ResponseEntity<>(messageDTO, HttpStatus.OK);
        } else {
            messageDTO.setMessage("password cannot be updated");
            return new ResponseEntity<>(messageDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("update-address/{id}")
    public ResponseEntity<MessageDTO> updatePassword(HttpServletRequest request, @PathVariable Long id, @Valid @RequestBody AddressUpdateDTO addressUpdateDto) {
        String email = request.getUserPrincipal().getName();
        return sellerImpl.updateAddress(email, id, addressUpdateDto);
    }

    @GetMapping("/view-categories")
    public List<SellerCategoryResponseDTO> showCategories() {

        return categoryImpl.showSellerCategories();
    }

    @PostMapping("/add-product")
    public ResponseEntity<MessageDTO> addProduct(HttpServletRequest request,
                                                 @RequestBody SellerProductAddDTO sellerProductAddDTO) {
        String email = request.getUserPrincipal().getName();
        if (productImpl.addProduct(email, sellerProductAddDTO)) {
            messageDTO.setMessage("product has been added");
            return new ResponseEntity<>(messageDTO, HttpStatus.CREATED);
        }
        messageDTO.setMessage("product already exists");
        return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete-product")
    public ResponseEntity<MessageDTO> deleteProduct(HttpServletRequest request, @RequestParam Long id) {
        String email = request.getUserPrincipal().getName();
        if (productImpl.deleteProduct(email, id)) {
            messageDTO.setMessage("product deleted successfully");
            return new ResponseEntity<>(messageDTO, HttpStatus.OK);
        }
        messageDTO.setMessage("product doesn't exist in the database");
        return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update-product")
    public ResponseEntity<MessageDTO> updateProduct(HttpServletRequest request,
                                                    @RequestBody SellerProductUpdateDTO sellerProductUpdateDTO,
                                                    @RequestParam Long productId) {
        String email = request.getUserPrincipal().getName();
        if (productImpl.updateProduct(email, sellerProductUpdateDTO, productId)) {
            messageDTO.setMessage("product updated successfully");
            return new ResponseEntity<>(messageDTO, HttpStatus.OK);
        }
        messageDTO.setMessage("Product cannot be updated");
        return new ResponseEntity<>(messageDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/view-product")
    public SellerProductShowDTO getProduct(HttpServletRequest request, @RequestParam Long productId) {
        String email = request.getUserPrincipal().getName();
        return productImpl.showSellerProduct(email, productId);
    }

    @GetMapping("/view-products")
    public List<SellerProductShowDTO> getProducts(HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        return productImpl.showAllSellerProducts(email);
    }

    @PostMapping("/add-productVariation")
    public ResponseEntity<MessageDTO> addProductVariation(HttpServletRequest request,
                                                          @Valid @RequestBody ProductVariationDTO productVariationDTO) {
        String email = request.getUserPrincipal().getName();
        if (productImpl.addVariation(email, productVariationDTO)) {
            messageDTO.setMessage("product variation added successfully");
            return new ResponseEntity<>(messageDTO, HttpStatus.CREATED);
        }
        messageDTO.setMessage("product variation cannot be added");
        return new ResponseEntity<>(messageDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/update-productVariation")
    public ResponseEntity<MessageDTO> updateVariation(HttpServletRequest request,
                                                      @RequestBody ProductVariationUpdateDTO productVariationUpdateDTO, @RequestParam Long id) {
        String email = request.getUserPrincipal().getName();
        if (productImpl.updateVariation(email, productVariationUpdateDTO, id)) {
            messageDTO.setMessage("product variation updated successfully");
            return new ResponseEntity<>(messageDTO, HttpStatus.OK);
        }
        messageDTO.setMessage("product cannot be updated");
        return new ResponseEntity<>(messageDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/view-variation")
    public MappingJacksonValue viewVariation(HttpServletRequest request, @RequestParam Long id) {
        String email = request.getUserPrincipal().getName();
        ProductVariationResponseDTO responseDTO = productImpl.showVariation(email, id);
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("primaryImageName", "variationId", "quantityAvailable"
                , "price", "metadata", "active", "productId", "productDTO");

        FilterProvider filters = new SimpleFilterProvider().addFilter("responseDTOFilter", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(responseDTO);
        mapping.setFilters(filters);
        return mapping;
    }

    @GetMapping("/view-allVariations/{productId}")
    public MappingJacksonValue viewVariations(HttpServletRequest request, @PathVariable Long productId) {
        List<ProductVariationResponseDTO> responseDTOList = productImpl.showProductVariations(request.getUserPrincipal().getName(), productId);

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("primaryImageName", "variationId", "quantityAvailable"
                , "price", "metadata", "active");

        FilterProvider filters = new SimpleFilterProvider().addFilter("responseDTOFilter", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(responseDTOList);
        mapping.setFilters(filters);
        return mapping;
    }


}
