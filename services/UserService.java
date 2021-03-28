package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.entities.*;
import com.Bootcamp.Project.Application.enums.Label;
import com.Bootcamp.Project.Application.repositories.AddressRepository;
import com.Bootcamp.Project.Application.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    AddressRepository addressRepository;
/*
    public void check(){
        User user=userRepository.findByEmail("adminuser1@gmail.com");
        System.out.println(user.getEmail());
    }*/

    public void createUsingJson() {
        ObjectMapper mapper = new ObjectMapper();

        /*Seller seller = null;
        Admin admin = null;
        Customer customer = null;*/
/*
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
        }*/
        /*userRepository.save(seller);
        userRepository.save(admin);*/
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        Customer customer3 = new Customer();
        customer3.setEmail("adityaalp1996@gmail.com");
        customer1.setEmail("rajput.aditya1996@gmail.com@gmail.com");
        customer2.setEmail("aklankapati1996@gmail.com");
        userRepository.save(customer1);
        userRepository.save(customer2);
        userRepository.save(customer3);

        Address address1 = new Address();
        Address address2 = new Address();
        Address address3 = new Address();

        Name name = new Name();
        Name name2 = new Name();
        Name name3 = new Name();
        name.setLastName("singh");
        name.setFirstName("aditya");
        name2.setLastName("negi");
        name2.setFirstName("akshay");
        name3.setLastName("rikhari");
        name3.setFirstName("anju");

        address1.setLabel(Label.OFFICE);
        address1.setAddressLine("232-c");
        address1.setCity("delhi");
        address2.setLabel(Label.OFFICE);
        address2.setAddressLine("332-d");
        address2.setCity("mumbai");
        address3.setLabel(Label.OFFICE);
        address3.setAddressLine("765");
        address3.setCity("punjab");

        Seller seller1 = new Seller();
        Seller seller2 = new Seller();
        Seller seller3 = new Seller();

        seller1.setEmail("meloida@");
        seller2.setEmail("deathadder@");
        seller3.setEmail("alpha@");
        seller1.setAddress(address1);
        seller2.setAddress(address2);
        seller3.setAddress(address3);
        seller1.setName(name);
        seller2.setName(name2);
        seller3.setName(name3);
        userRepository.save(seller1);
        userRepository.save(seller3);
        userRepository.save(seller2);

    }

    @Transactional
    public void createCustomer() {
        Customer customer=new Customer();
        Name name=new Name();
        Address address1=new Address();
        address1.setCity("delhi");
        address1.setAddressLine("111");
        address1.setLabel(Label.HOME);
        address1.setCountry("india");
        Address address2=new Address();
        address2.setCity("pattaya");
        address2.setAddressLine("222");
        address2.setLabel(Label.OFFICE);
        address2.setCountry("bangkok");
        name.setFirstName("raman");
        name.setLastName("singh");
        customer.setEmail("delhi@gmail.com");
        customer.setContact("1005432");
        customer.setName(name);
        List<Address> addressList=new ArrayList<>();
        addressList.add(address1);
        addressList.add(address2);
        addressRepository.save(address1);
        addressRepository.save(address2);
       customer.setAddressSet(addressList);
        userRepository.save(customer);
    }
}


