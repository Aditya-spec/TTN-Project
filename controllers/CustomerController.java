package com.Bootcamp.Project.Application.controllers;

import com.Bootcamp.Project.Application.dtos.*;
import com.Bootcamp.Project.Application.services.CategoryImpl;
import com.Bootcamp.Project.Application.services.CustomerImpl;
import com.Bootcamp.Project.Application.services.ProductImpl;
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
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerImpl customerImpl;
    @Autowired
    CategoryImpl categoryImpl;
    @Autowired
    ProductImpl productImpl;

    @GetMapping("/home")
    public String indexPremium() {
        return "Customer Home";
    }

    @GetMapping("/view-profile")
    public CustomerProfileDTO showProfile(HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        return customerImpl.showProfile(email);
    }

    @GetMapping("/view-addresses")
    public List<AddressDTO> showAddressList(HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        return customerImpl.showAddresses(email);
    }

    @PostMapping("/add-address")
    public ResponseEntity<String> addAddress(HttpServletRequest request, @Valid @RequestBody AddressDTO addressDTO) {
        String email = request.getUserPrincipal().getName();
        if (customerImpl.addAddress(email, addressDTO)) {
            return new ResponseEntity<>("address added successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("address can't be updated", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete-address")
    public ResponseEntity<String> deleteAddress(@RequestParam Long id) {
        if (customerImpl.deleteAddress(id)) {
            return new ResponseEntity<>("address deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("address couldn't be deleted", HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/update-profile")
    public ResponseEntity<String> updateCustomer(HttpServletRequest request, @Valid @RequestBody CustomerProfileDTO customerProfileDto) {
        String email = request.getUserPrincipal().getName();
        if (customerImpl.updateProfile(email, customerProfileDto)) {
            return new ResponseEntity<>("fields updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("fields cannot be updated", HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<String> changePassword(HttpServletRequest request, @RequestBody PasswordDTO passwordDto) {
        String email = request.getUserPrincipal().getName();
        if (customerImpl.checkPassword(passwordDto.getPassword(), passwordDto.getConfirmPassword())) {
            return new ResponseEntity<>("Password and Confirm password do not match", HttpStatus.BAD_REQUEST);
        }
        if (customerImpl.customerResetPassword(email, passwordDto)) {
            return new ResponseEntity<>("password has been updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("password cannot be updated", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("update-address")
    public ResponseEntity<String> updatePassword(@RequestParam Long id, HttpServletRequest request, @Valid @RequestBody AddressUpdateDTO addressUpdateDto) {
        return customerImpl.updateAddress(id, addressUpdateDto);
    }

    @GetMapping("/view-categories")
    public List<CustomerCategoryResponseDTO> showCategories() {
        return categoryImpl.showCustomerCategories();
    }

    @GetMapping("view-categories/{id}")
    public CustomerCategoryResponseDTO showCategoriesParam(@PathVariable Long id) {
        return categoryImpl.showCustomerCategoriesParam(id);
    }

    @GetMapping("/view-product")
    public MappingJacksonValue viewProduct(@RequestParam Long id) {
        AdminCustomerProductResponseDTO responseDTO = productImpl.showCustomerProduct(id);

        SimpleBeanPropertyFilter filter=SimpleBeanPropertyFilter.serializeAllExcept("productDTO");
        FilterProvider filters = new SimpleFilterProvider().addFilter("responseDTOFilter", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(responseDTO);
        mapping.setFilters(filters);
        return mapping;

    }

    @GetMapping("/view-allProducts")
    public MappingJacksonValue viewAllProducts(@RequestParam Long categoryId){
       List<AdminCustomerProductResponseDTO> responseDTOList= productImpl.showCustomerProducts(categoryId);
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept("productDTO");
        FilterProvider filters = new SimpleFilterProvider().addFilter("responseDTOFilter", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(responseDTOList);
        mapping.setFilters(filters);
        return mapping;
    }

    @GetMapping("/view-similarProducts")
    public MappingJacksonValue viewSimilarProduct(@RequestParam Long id){
        List<AdminCustomerProductResponseDTO> responseDTOList=productImpl.viewSimilarProduct(id);
        SimpleBeanPropertyFilter filter=SimpleBeanPropertyFilter.serializeAllExcept("productDTO");
        FilterProvider filters = new SimpleFilterProvider().addFilter("responseDTOFilter", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(responseDTOList);
        mapping.setFilters(filters);
        return mapping;

    }

}
