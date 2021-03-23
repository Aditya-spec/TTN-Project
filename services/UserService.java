package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.entities.*;
import com.Bootcamp.Project.Application.enums.Label;
import com.Bootcamp.Project.Application.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void createUsingJson() {
        ObjectMapper mapper=new ObjectMapper();

        Seller seller= null;
        Admin admin=null;
        Customer customer=null;

        try {
            seller = mapper.readValue(new File("/home/ttn/JsonData/SellerData.json"), Seller.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            admin=mapper.readValue(new File("/home/ttn/JsonData/AdminData.json"),Admin.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            customer=mapper.readValue(new File("/home/ttn/JsonData/CustomerData.json"),Customer.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        userRepository.save(seller);
        userRepository.save(admin);
        userRepository.save(customer);
    }
}


