package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.dtos.AddressDTO;
import com.Bootcamp.Project.Application.dtos.MessageDTO;
import com.Bootcamp.Project.Application.dtos.RegisteredCustomerDTO;
import com.Bootcamp.Project.Application.dtos.RegisteredSellerDTO;
import com.Bootcamp.Project.Application.entities.*;
import com.Bootcamp.Project.Application.enums.ErrorCode;
import com.Bootcamp.Project.Application.exceptionHandling.EcommerceException;
import com.Bootcamp.Project.Application.repositories.CustomerRepository;
import com.Bootcamp.Project.Application.repositories.SellerRepository;
import com.Bootcamp.Project.Application.repositories.UserRepository;
import com.Bootcamp.Project.Application.services.serviceInterfaces.AdminService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    MessageDTO messageDTO;
    @Autowired
    PaginationImpl paginationImpl;

    @Autowired
    EmailService emailService;

    Logger logger= LoggerFactory.getLogger(getClass().getName());

    ModelMapper modelMapper = new ModelMapper();

    public List<RegisteredCustomerDTO> getCustomers(int offset, int size) {
        List<Customer> customerList = customerRepository.fetchCustomerByPage(paginationImpl.pagination(offset,size));
        if (customerList.size() == 0) {
            throw new EcommerceException(ErrorCode.NO_DATA);
        }

        List<RegisteredCustomerDTO> registeredCustomerDTOList
                = modelMapper.map(customerList, new TypeToken<List<RegisteredCustomerDTO>>() {
        }.getType());

        return registeredCustomerDTOList;
    }

    public List<RegisteredSellerDTO> getSellers(int offset, int size) {
        List<Seller> sellerList = sellerRepository.fetchSellerByPage(paginationImpl.pagination(offset,size));
        if (sellerList.size() == 0) {
            throw new EcommerceException(ErrorCode.USER_NOT_FOUND);
        }

        List<RegisteredSellerDTO> registeredSellerDTOList = new ArrayList<>();
        for (Seller seller : sellerList) {
            AddressDTO addressDTO = modelMapper.map(seller.getAddress(), AddressDTO.class);
            addressDTO.setDateCreated(seller.getAddress().getDateCreated());
            RegisteredSellerDTO registeredSellerDTO = modelMapper.map(seller, RegisteredSellerDTO.class);

            registeredSellerDTO.setAddressDTO(addressDTO);
            registeredSellerDTO.setContactNumber(seller.getCompanyContact());
            registeredSellerDTOList.add(registeredSellerDTO);
        }
        return registeredSellerDTOList;
    }


    public ResponseEntity<MessageDTO> activateUser(Long id, Map<Object, Object> fields) {
        User user = userRepository.findById(id).get();

        if (user == null) {
            throw new EcommerceException(ErrorCode.USER_NOT_FOUND);
        }

        if (user.getActive()) {
            messageDTO.setMessage("User is already active");
            return new ResponseEntity(messageDTO, HttpStatus.BAD_REQUEST);
        }
        try {
            fields.forEach((k, v) -> {
                Field field = ReflectionUtils.findField(User.class, (String) k);
                field.setAccessible(true);
                ReflectionUtils.setField(field, user, v);
            });
        } catch (RuntimeException e) {
            throw new EcommerceException(ErrorCode.INVALID_FIELDS);

        }
        userRepository.save(user);
        String body = "Your account is active now!!";
        String topic = "Account Update!!";
        emailService.sendMail(user.getEmail(), topic, body);
        messageDTO.setMessage("Account is activated successfully");
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }


    public ResponseEntity<MessageDTO> deactivateUser(Long id, Map<Object, Object> fields) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new EcommerceException(ErrorCode.USER_NOT_FOUND);
        }

        List<Role> roles = user.getRoles();
        for (Role role : roles) {
            if (role.getAuthorization().equals("ROLE_ADMIN")) {
                throw new EcommerceException(ErrorCode.USER_IS_ADMIN);
            }
        }
        if (!user.getActive()) {
            messageDTO.setMessage("User is already deactivated");
            return new ResponseEntity(messageDTO, HttpStatus.BAD_REQUEST);
        }
        try {
            fields.forEach((k, v) -> {
                Field field = ReflectionUtils.findField(User.class, (String) k);
                field.setAccessible(true);
                ReflectionUtils.setField(field, user, v);
            });
        } catch (RuntimeException e) {
            throw new EcommerceException(ErrorCode.INVALID_FIELDS);

        }
        userRepository.save(user);
        String body = "Your account is deactivated now!!";
        String topic = "Account Update";
        emailService.sendMail(user.getEmail(), topic, body);
        messageDTO.setMessage("account is deactivated successfully");
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }


}

