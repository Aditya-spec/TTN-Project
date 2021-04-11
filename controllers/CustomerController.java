package com.Bootcamp.Project.Application.controllers;

import com.Bootcamp.Project.Application.dtos.*;
import com.Bootcamp.Project.Application.enums.ErrorCode;
import com.Bootcamp.Project.Application.enums.Label;
import com.Bootcamp.Project.Application.exceptionHandling.EcommerceException;
import com.Bootcamp.Project.Application.services.CategoryImpl;
import com.Bootcamp.Project.Application.services.CustomerImpl;
import com.Bootcamp.Project.Application.services.ImageImpl;
import com.Bootcamp.Project.Application.services.ProductImpl;
import com.Bootcamp.Project.Application.validations.CustomValidation;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
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
    @Autowired
    ImageImpl imageImpl;
    @Autowired
    MessageDTO messageDTO;
    @Autowired
    CustomValidation customValidation;

    @GetMapping("/home")
    public ResponseEntity<MessageDTO> indexPremium() {
        messageDTO.setMessage("Customer Home");
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
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
    public ResponseEntity<MessageDTO> addAddress(HttpServletRequest request, @Valid @RequestBody AddressDTO addressDTO) {
        String email = request.getUserPrincipal().getName();
        Label label = customValidation.verifyLabel(addressDTO.getLabel());
        if (customerImpl.addAddress(email, addressDTO)) {
            messageDTO.setMessage("Address added successfully");
            return new ResponseEntity<>(messageDTO, HttpStatus.OK);
        }
        messageDTO.setMessage("Address can't be updated");
        return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete-address")
    public ResponseEntity<MessageDTO> deleteAddress(HttpServletRequest request, @RequestParam Long id) {
        String email = request.getUserPrincipal().getName();
        if (customerImpl.deleteAddress(id, email)) {
            messageDTO.setMessage("Address deleted successfully");
            return new ResponseEntity<>(messageDTO, HttpStatus.OK);
        }
        messageDTO.setMessage("Address can't be deleted");
        return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/upload-image")
    public ResponseEntity<MessageDTO> uploadImage(@RequestBody MultipartFile imageFile, HttpServletRequest request) {
        customValidation.imageValidation(imageFile);
        String email=request.getUserPrincipal().getName();
        try {
            return imageImpl.uploadImage(imageFile, email);
        } catch (IOException e) {
            throw new EcommerceException(ErrorCode.IMAGE_NOT_UPLOADED);
        }
    }


    @PatchMapping("/update-profile")
    public ResponseEntity<MessageDTO> updateCustomer(HttpServletRequest request, @Valid @RequestBody CustomerProfileDTO customerProfileDto) {
        String email = request.getUserPrincipal().getName();
        if (customerImpl.updateProfile(email, customerProfileDto)) {
            messageDTO.setMessage("Fields updated successfully");
            return new ResponseEntity<>(messageDTO, HttpStatus.OK);
        }
        messageDTO.setMessage("Fields cannot be updated");
        return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<MessageDTO> changePassword(HttpServletRequest request, @Valid @RequestBody PasswordDTO passwordDto) {
        String email = request.getUserPrincipal().getName();
        if (customerImpl.checkPassword(passwordDto.getPassword(), passwordDto.getConfirmPassword())) {
            messageDTO.setMessage("Password and Confirm password do not match");
            return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
        }
        if (customerImpl.customerResetPassword(email, passwordDto)) {
            messageDTO.setMessage("password has been updated successfully");
            return new ResponseEntity<>(messageDTO, HttpStatus.OK);
        } else {
            messageDTO.setMessage("password cannot be updated");
            return new ResponseEntity<>(messageDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/update-address")
    public ResponseEntity<MessageDTO> updatePassword(@RequestParam Long addressId, HttpServletRequest request, @Valid @RequestBody AddressUpdateDTO addressUpdateDto) {
        String email = request.getUserPrincipal().getName();
        return customerImpl.updateAddress(email, addressId, addressUpdateDto);
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

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept("productDTO");
        FilterProvider filters = new SimpleFilterProvider().addFilter("responseDTOFilter", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(responseDTO);
        mapping.setFilters(filters);
        return mapping;
    }

    @GetMapping("/view-allProducts")
    public MappingJacksonValue viewAllProducts(@RequestParam Long categoryId) {
        List<AdminCustomerProductResponseDTO> responseDTOList = productImpl.showCustomerProducts(categoryId);
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept("productDTO");
        FilterProvider filters = new SimpleFilterProvider().addFilter("responseDTOFilter", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(responseDTOList);
        mapping.setFilters(filters);
        return mapping;
    }

    @GetMapping("/view-similarProducts")
    public MappingJacksonValue viewSimilarProduct(@RequestParam Long productId,@RequestParam("offset") int offset,
                                                  @RequestParam("size") int size) {
        List<AdminCustomerProductResponseDTO> responseDTOList = productImpl.viewSimilarProduct(productId,offset,size);
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept("productDTO");
        FilterProvider filters = new SimpleFilterProvider().addFilter("responseDTOFilter", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(responseDTOList);
        mapping.setFilters(filters);
        return mapping;

    }

    @GetMapping("/category-filter")
    public CustomerCategoryFilterDTO categoryFilter(@RequestParam Long categoryId) {
        CustomerCategoryFilterDTO customerCategoryFilterDTO = categoryImpl.filter(categoryId);
        return customerCategoryFilterDTO;
    }

}
