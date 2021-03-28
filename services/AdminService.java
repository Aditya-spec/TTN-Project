package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.dtos.CustomerDto;
import com.Bootcamp.Project.Application.dtos.RegisteredCustomerDto;
import com.Bootcamp.Project.Application.dtos.RegisteredSellerDto;
import com.Bootcamp.Project.Application.dtos.SellerDto;
import com.Bootcamp.Project.Application.entities.Customer;
import com.Bootcamp.Project.Application.entities.Role;
import com.Bootcamp.Project.Application.entities.Seller;
import com.Bootcamp.Project.Application.entities.User;
import com.Bootcamp.Project.Application.exceptionHandling.InvalidFieldException;
import com.Bootcamp.Project.Application.exceptionHandling.NotFoundException;
import com.Bootcamp.Project.Application.repositories.CustomerRepository;
import com.Bootcamp.Project.Application.repositories.SellerRepository;
import com.Bootcamp.Project.Application.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    Pageable sortById = PageRequest.of(0, 10, Sort.by("id"));


      /*public List<RegisteredCustomerDto> getCustomers() {
        ModelMapper modelMapper = new ModelMapper();

        List<Customer> customerList = (List<Customer>) customerRepository.findAll(sortById);
        System.out.println(customerList);

        List<RegisteredCustomerDto> registeredCustomerDtoList
                = modelMapper.map(customerList, new TypeToken<List<RegisteredCustomerDto>>() {
        }.getType());

        System.out.println(registeredCustomerDtoList);
        return registeredCustomerDtoList;
    }*/

     /*public List<CustomerDto> getCustomers() {
        ModelMapper modelMapper = new ModelMapper();
        List<Customer> customerList = customerRepository.fetchAllCustomer();
        System.out.println(customerList);

        List<CustomerDto> customerDtoList
                = modelMapper.map(customerList, new TypeToken<List<CustomerDto>>() {
        }.getType());

        System.out.println(customerDtoList);
        return customerDtoList;
    }*/

    public List<RegisteredCustomerDto> getCustomers() {
        ModelMapper modelMapper = new ModelMapper();
        List<Customer> customerList = customerRepository.fetchCustomerByPage(sortById);
        if (customerList == null) {
            throw new NotFoundException("No customer registered");
        }

        List<RegisteredCustomerDto> registeredCustomerDtoList
                = modelMapper.map(customerList, new TypeToken<List<RegisteredCustomerDto>>() {
        }.getType());

        return registeredCustomerDtoList;
    }










    /*public List<SellerDto> getSellers() {
        ModelMapper modelMapper = new ModelMapper();
        List<Seller> sellerList = sellerRepository.fetchAllSeller();
        List<SellerDto> sellerDtoList
                = modelMapper.map(sellerList, new TypeToken<List<SellerDto>>() {
        }.getType());
        return sellerDtoList;

    }*/


    public List<RegisteredSellerDto> getSellers() {
        ModelMapper modelMapper = new ModelMapper();
        List<Seller> sellerList = sellerRepository.fetchSellerByPage(sortById);
        if (sellerList == null) {
            throw new NotFoundException("No seller registered");
        }

        List<RegisteredSellerDto> registeredSellerDtoList
                = modelMapper.map(sellerList, new TypeToken<List<RegisteredSellerDto>>() {
        }.getType());

        System.out.println(registeredSellerDtoList);
        return registeredSellerDtoList;
    }


    public ResponseEntity<String> activateUser(Long id, Map<Object, Object> fields) {
        User user = userRepository.findById(id).get();

        if (user == null) {
            return new ResponseEntity("User with this id doesn't exist", HttpStatus.BAD_REQUEST);
        }

        if (user.getActive()) {
            return new ResponseEntity("User is already activated", HttpStatus.BAD_REQUEST);
        }
        try {
            fields.forEach((k, v) -> {
                Field field = ReflectionUtils.findField(User.class, (String) k);
                field.setAccessible(true);
                ReflectionUtils.setField(field, user, v);
            });
        } catch (RuntimeException e) {
            throw new InvalidFieldException("Field is not present");
        }
        userRepository.save(user);
        String body = "Your account is activated now!!";
        String topic = "Account Update";
        emailService.sendMail(user.getEmail(), topic, body);
        return new ResponseEntity<>("account is activated successfully", HttpStatus.OK);
    }

    public ResponseEntity<String> deactivateUser(Long id, Map<Object, Object> fields) {
        User user = userRepository.findById(id).get();

        if (user == null) {
            return new ResponseEntity("User with this id doesn't exist", HttpStatus.BAD_REQUEST);
        }
        List<Role> roles = user.getRoles();
        for (Role role : roles) {
            if (role.getAuthorization().equals("ROLE_ADMIN"))
                return new ResponseEntity<>("Admin cannot be deactivated", HttpStatus.BAD_REQUEST);
        }
        if (!user.getActive()) {
            return new ResponseEntity("User is already deactivated", HttpStatus.BAD_REQUEST);
        }
        try {
            fields.forEach((k, v) -> {
                Field field = ReflectionUtils.findField(User.class, (String) k);
                field.setAccessible(true);
                ReflectionUtils.setField(field, user, v);
            });
        } catch (RuntimeException e) {
            throw new InvalidFieldException("Invalid Field");
        }
        userRepository.save(user);
        String body = "Your account is deactivated now!!";
        String topic = "Account Update";
        emailService.sendMail(user.getEmail(), topic, body);
        return new ResponseEntity<>("account is deactivated successfully", HttpStatus.OK);
    }


}

