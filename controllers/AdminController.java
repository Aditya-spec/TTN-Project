package com.Bootcamp.Project.Application.controllers;

import com.Bootcamp.Project.Application.dtos.RegisteredCustomerDto;
import com.Bootcamp.Project.Application.dtos.RegisteredSellerDto;
import com.Bootcamp.Project.Application.services.AdminService;
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
    AdminService adminService;

    @GetMapping("/home")
    public String adminHome() {
        return "Admin home";
    }


    @GetMapping("/get/customers")
    public ResponseEntity<List<RegisteredCustomerDto>> getAllCustomer() {
        return new ResponseEntity<>(adminService.getCustomers(), HttpStatus.OK);
    }


    @GetMapping("/get/sellers")
    public ResponseEntity<List<RegisteredSellerDto>> getAllSeller() {
        return new ResponseEntity<>(adminService.getSellers(), HttpStatus.OK);
    }

    @PatchMapping(path = "/activate-user/{id}")
    public ResponseEntity<String> activateUser(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {
        return adminService.activateUser(id, fields);
    }


    @PatchMapping("/deactivate-user/{id}")
    public ResponseEntity<String> deactivateUser(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {
        return adminService.deactivateUser(id, fields);
    }
/*
www.yoursite.com?myparam1={id1}&myparam2={id2} */

     /*@GetMapping("/get/sellers")
    public List<SellerDto> getAllSeller( ){
        //List<CustomerDto> customerDtos = adminService.getCustomers();
        List<SellerDto> sellerDtoList = adminService.getSellers();
        return sellerDtoList;

    }*/

    /* @GetMapping("/get/customers")
    public List<CustomerDto> getAllCustomer( ){
        List<CustomerDto> customerDtoList = adminService.getCustomers();
    return customerDtoList;
    }*/

}


