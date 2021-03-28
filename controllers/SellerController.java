package com.Bootcamp.Project.Application.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
public class SellerController {
    @GetMapping("/home")
    public String userHome(){
        return "Seller home";
    }
}
