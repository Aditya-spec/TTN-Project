package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.dtos.AddressDTO;
import com.Bootcamp.Project.Application.dtos.RegisteredCustomerDTO;
import com.Bootcamp.Project.Application.dtos.RegisteredSellerDTO;
import com.Bootcamp.Project.Application.entities.Customer;
import com.Bootcamp.Project.Application.entities.Role;
import com.Bootcamp.Project.Application.entities.Seller;
import com.Bootcamp.Project.Application.entities.User;
import com.Bootcamp.Project.Application.enums.ErrorCode;
import com.Bootcamp.Project.Application.exceptionHandling.EcommerceException;
import com.Bootcamp.Project.Application.repositories.CustomerRepository;
import com.Bootcamp.Project.Application.repositories.SellerRepository;
import com.Bootcamp.Project.Application.repositories.UserRepository;
import com.Bootcamp.Project.Application.services.serviceInterfaces.AdminService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AdminImpl implements AdminService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    Pageable sortById = PageRequest.of(0, 10, Sort.by("id"));


      /*public List<RegisteredCustomerDTO> getCustomers() {
        ModelMapper modelMapper = new ModelMapper();

        List<Customer> customerList = (List<Customer>) customerRepository.findAll(sortById);
        System.out.println(customerList);

        List<RegisteredCustomerDTO> registeredCustomerDtoList
                = modelMapper.map(customerList, new TypeToken<List<RegisteredCustomerDTO>>() {
        }.getType());

        System.out.println(registeredCustomerDtoList);
        return registeredCustomerDtoList;
    }*/

     /*public List<CustomerDTO> getCustomers() {
        ModelMapper modelMapper = new ModelMapper();
        List<Customer> customerList = customerRepository.fetchAllCustomer();
        System.out.println(customerList);

        List<CustomerDTO> customerDtoList
                = modelMapper.map(customerList, new TypeToken<List<CustomerDTO>>() {
        }.getType());

        System.out.println(customerDtoList);
        return customerDtoList;
    }*/

    public List<RegisteredCustomerDTO> getCustomers() {
        ModelMapper modelMapper = new ModelMapper();
        List<Customer> customerList = customerRepository.fetchCustomerByPage(sortById);
        if (customerList == null) {
            throw new EcommerceException(ErrorCode.USER_NOT_FOUND);
            //throw new NotFoundException("No customer registered");
        }

        List<RegisteredCustomerDTO> registeredCustomerDTOList
                = modelMapper.map(customerList, new TypeToken<List<RegisteredCustomerDTO>>() {
        }.getType());

        return registeredCustomerDTOList;
    }

    /*public List<SellerDTO> getSellers() {
        ModelMapper modelMapper = new ModelMapper();
        List<Seller> sellerList = sellerRepository.fetchAllSeller();
        List<SellerDTO> sellerDtoList
                = modelMapper.map(sellerList, new TypeToken<List<SellerDTO>>() {
        }.getType());
        return sellerDtoList;

    }*/

    public List<RegisteredSellerDTO> getSellers() {
        ModelMapper modelMapper = new ModelMapper();
        List<Seller> sellerList = sellerRepository.fetchSellerByPage(sortById);
        if (sellerList == null) {
            throw new EcommerceException(ErrorCode.USER_NOT_FOUND);
            // throw new NotFoundException("No seller registered");
        }

        List<RegisteredSellerDTO> registeredSellerDTOList = new ArrayList<>();
        for (Seller seller : sellerList) {
            AddressDTO addressDTO = modelMapper.map(seller.getAddress(), AddressDTO.class);
            RegisteredSellerDTO registeredSellerDTO = modelMapper.map(seller, RegisteredSellerDTO.class);
            registeredSellerDTO.setAddressDTO(addressDTO);
            registeredSellerDTOList.add(registeredSellerDTO);
        }
        /*List<RegisteredSellerDTO> registeredSellerDTOList
                = modelMapper.map(sellerList, new TypeToken<List<RegisteredSellerDTO>>() {
        }.getType());*/

        System.out.println(registeredSellerDTOList);
        return registeredSellerDTOList;
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
            throw new EcommerceException(ErrorCode.INVALID_FIELDS);
            // throw new InvalidFieldException("Field is not present");
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
            throw new EcommerceException(ErrorCode.INVALID_FIELDS);
            //throw new InvalidFieldException("Invalid Field");
        }
        userRepository.save(user);
        String body = "Your account is deactivated now!!";
        String topic = "Account Update";
        emailService.sendMail(user.getEmail(), topic, body);
        return new ResponseEntity<>("account is deactivated successfully", HttpStatus.OK);
    }
}

