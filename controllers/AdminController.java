package com.Bootcamp.Project.Application.controllers;

import com.Bootcamp.Project.Application.dtos.*;
import com.Bootcamp.Project.Application.services.AdminImpl;
import com.Bootcamp.Project.Application.services.CategoryImpl;
import com.Bootcamp.Project.Application.services.ProductImpl;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminImpl adminImpl;
    @Autowired
    CategoryImpl categoryImpl;
    @Autowired
    ProductImpl productImpl;

    @GetMapping("/home")
    public String adminHome() {
        return "Admin home";
    }


    @GetMapping("/get/customers")
    public ResponseEntity<List<RegisteredCustomerDTO>> getAllCustomer() {
        return new ResponseEntity<>(adminImpl.getCustomers(), HttpStatus.OK);
    }


    @GetMapping("/get/sellers")
    public ResponseEntity<List<RegisteredSellerDTO>> getAllSeller() {
        return new ResponseEntity<>(adminImpl.getSellers(), HttpStatus.OK);
    }

    @PatchMapping(path = "/activate-user/{id}")
    public ResponseEntity<String> activateUser(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {
        return adminImpl.activateUser(id, fields);
    }


    @PatchMapping("/deactivate-user/{id}")
    public ResponseEntity<String> deactivateUser(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {
        return adminImpl.deactivateUser(id, fields);
    }

    @PostMapping("/add-metadataField")
    public ResponseEntity<String> addMetadata(@RequestParam String metaDataField) {
        String result = categoryImpl.addMetadata(metaDataField);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/view-metaDataField")
    public List<CategoryMetadataFieldDTO> showMetaDataFields() {
        return categoryImpl.showMetaData();
    }


    @PostMapping("/add-category")
    public ResponseEntity<String> addCategory(@Valid @RequestBody CategoryAddDTO categoryAddDTO) {
        String result = categoryImpl.addCategory(categoryAddDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/view-categories")
    public List<CategoryResponseDTO> viewCategories() {
        return categoryImpl.showCategories();
    }

    @GetMapping("/view-category/{id}")
    public CategoryResponseDTO viewCategory(@PathVariable Long id) {
        return categoryImpl.showCategory(id);
    }

    @PutMapping("/update-category")
    public ResponseEntity<String> updateCategory(@RequestParam Long id, String updatedName) {
        String result = categoryImpl.updateCategory(id, updatedName);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/add-metaValues")
    public ResponseEntity<String> addMetaValues(@Valid @RequestBody CategoryMetadataFieldValuesDTO categoryMetadataFieldValuesDTO) {
        String result = categoryImpl.addMetadataValues(categoryMetadataFieldValuesDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/update-metaValues")
    public ResponseEntity<String> update(@Valid @RequestBody CategoryMetadataFieldValuesDTO categoryMetadataFieldValuesDTO) {
        String result = categoryImpl.updateMetadataValues(categoryMetadataFieldValuesDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/activate-product")
    public ResponseEntity<String> activateProduct(@RequestParam Long id) {
        if (productImpl.activateProduct(id)) {
            return new ResponseEntity<>("Product has been activated and mail has been sent", HttpStatus.OK);
        }
        return new ResponseEntity<>("product doesn't exist in the database", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/deActivate-product")
    public ResponseEntity<String> deActivateProduct(@RequestParam Long id) {
        if (productImpl.deActivateProduct(id)) {
            return new ResponseEntity<>("Product has been deActivated and mail has been sent", HttpStatus.OK);
        }
        return new ResponseEntity<>("product doesn't exist in the database", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/view-allProducts")
    public MappingJacksonValue viewAllProducts() {
        List<AdminCustomerProductResponseDTO> responseDTOList = productImpl.showAllAdminProducts();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept("productDTO");
        FilterProvider filters = new SimpleFilterProvider().addFilter("responseDTOFilter", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(responseDTOList);
        mapping.setFilters(filters);
        return mapping;
    }

    @GetMapping("/view-product")
    public MappingJacksonValue viewProduct(@RequestParam Long id) {
        AdminCustomerProductResponseDTO responseDTO = productImpl.showAdminProduct(id);


        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept("productDTO");
        FilterProvider filters = new SimpleFilterProvider().addFilter("responseDTOFilter", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(responseDTO);
        mapping.setFilters(filters);
        return mapping;

    }
}




    /*
  www.yoursite.com?myparam1={id1}&myparam2={id2} */

     /*@GetMapping("/get/sellers")
    public List<SellerDTO> getAllSeller( ){
        //List<CustomerDTO> customerDtos = adminService.getCustomers();
        List<SellerDTO> sellerDtoList = adminService.getSellers();
        return sellerDtoList;

    }*/

    /* @GetMapping("/get/customers")
    public List<CustomerDTO> getAllCustomer( ){
        List<CustomerDTO> customerDtoList = adminService.getCustomers();
    return customerDtoList;
    }*/




