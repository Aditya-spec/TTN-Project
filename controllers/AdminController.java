package com.Bootcamp.Project.Application.controllers;

import com.Bootcamp.Project.Application.dtos.*;
import com.Bootcamp.Project.Application.services.AdminImpl;
import com.Bootcamp.Project.Application.services.CategoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminImpl adminImpl;
    @Autowired
    CategoryImpl categoryImpl;

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
    public ResponseEntity<String> addMetadata(@RequestParam String metaDataField ){
        String result=categoryImpl.addMetadata(metaDataField);
        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }

    @GetMapping("/view-metaDataField")
    public List<CategoryMetadataFieldDTO> showMetaDataFields(){
       return categoryImpl.showMetaData();
    }


    @PostMapping("/add-category")
    public ResponseEntity<String> addCategory(@RequestBody CategoryAddDTO categoryAddDTO) {
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

    @PostMapping("/add-metaValues")
    public ResponseEntity<String> addMetaValues(@RequestBody CategoryMetadataFieldValuesDTO categoryMetadataFieldValuesDTO){
        String result=categoryImpl.addMetadataValues(categoryMetadataFieldValuesDTO);
        return new ResponseEntity<>(result,HttpStatus.CREATED);
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




