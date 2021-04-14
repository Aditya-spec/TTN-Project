package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.dtos.CustomerRegistrationDTO;
import com.Bootcamp.Project.Application.dtos.SellerRegistrationDTO;
import com.Bootcamp.Project.Application.entities.*;
import com.Bootcamp.Project.Application.enums.ErrorCode;
import com.Bootcamp.Project.Application.enums.Label;
import com.Bootcamp.Project.Application.exceptionHandling.EcommerceException;
import com.Bootcamp.Project.Application.repositories.CustomerRepository;
import com.Bootcamp.Project.Application.repositories.RoleRepository;
import com.Bootcamp.Project.Application.repositories.SellerRepository;
import com.Bootcamp.Project.Application.repositories.UserRepository;
import com.Bootcamp.Project.Application.services.serviceInterfaces.RegistrationService;
import com.Bootcamp.Project.Application.validations.CustomValidation;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class RegistrationImpl implements RegistrationService {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    CustomValidation customValidation;

    ModelMapper modelMapper = new ModelMapper();
    Logger logger= LoggerFactory.getLogger(getClass().getName());

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Boolean registerCustomer(CustomerRegistrationDTO customerRegistrationDTO) {
        Customer customer = modelMapper.map(customerRegistrationDTO, Customer.class);
        customer.setActive(false);
        String encodedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);

        Role role = roleRepository.findByAuthorization("ROLE_CUSTOMER").get(1);
        customer.setRoles(Arrays.asList(role));
        customer.setActivationToken(UUID.randomUUID().toString());
        customer.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        customerRepository.save(customer);

        String body = " You have been registered, please active your account using this link" +
                " which will be valid for 15 minutes only = \n http://localhost:8080/register-page/activate/customer/" + customer.getActivationToken();
        String topic = "Registration Done!!";

        System.out.println(body);

        emailService.sendMail(customer.getEmail(), topic, body);

        return true;
    }


    public Boolean activateCustomer(String token) {
        Customer registeredCustomer = customerRepository.findByActivationToken(token);
        LocalDateTime currentTime = LocalDateTime.now();
        if (currentTime.isAfter(registeredCustomer.getExpiresAt())) {
            registeredCustomer.setActive(false);
            registeredCustomer.setActivationToken(UUID.randomUUID().toString());
            registeredCustomer.setExpiresAt(LocalDateTime.now().plusMinutes(15));
            customerRepository.save(registeredCustomer);

            String body = " Your token has been expired, please active your account using this link" +
                    " which will be valid for 15 minutes only = \n http://localhost:8080/register-page/activate/customer/" + registeredCustomer.getActivationToken();

            System.out.println(body);
            emailService.sendMail(registeredCustomer.getEmail(), "Resending activation link", body);
            return false;
        } else {
            registeredCustomer.setActive(true);
            registeredCustomer.setActivationToken(null);
            registeredCustomer.setExpiresAt(null);
            customerRepository.save(registeredCustomer);
            return true;
        }
    }


    public boolean findCustomerByToken(String token) {
        Customer customer = customerRepository.findByActivationToken(token);
        if (customer == null) {
            throw new EcommerceException(ErrorCode.INVALID_TOKEN);
        }
        return true;
    }


    @Transactional
    public Boolean resendActivationLink(String email) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new EcommerceException(ErrorCode.USER_NOT_FOUND);
        }
        long id = user.getId();
        Customer customer = customerRepository.findById(id);

        if (customer == null) {
            throw new EcommerceException(ErrorCode.USER_NOT_FOUND);
        }
        if (customer.getActive()) {
            throw new EcommerceException(ErrorCode.ALREADY_ACTIVE);
        }
        customer.setActivationToken(UUID.randomUUID().toString());
        customer.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        customerRepository.save(customer);
        String body = " Please active your account using this link" +
                " which will be valid for 15 minutes only = \n http://localhost:8080/register-page/activate/customer/" + customer.getActivationToken();

        emailService.sendMail(user.getEmail(), "Resending activation Link", body);
        return true;
    }

    public boolean registerSeller(SellerRegistrationDTO sellerRegistrationDTO) {
        Address address = new Address();
        address = mapSellerAddress(sellerRegistrationDTO, address);

        Seller seller = modelMapper.map(sellerRegistrationDTO, Seller.class);

        seller.setAddress(address);
        seller.setActive(false);

        String encodedPassword = passwordEncoder.encode(seller.getPassword());
        seller.setPassword(encodedPassword);
        Role role = roleRepository.findByAuthorization("ROLE_SELLER").get(1);
        seller.setRoles(Arrays.asList(role));

        sellerRepository.save(seller);

        String body = "Your account has been registered, please wait for approval confirmation mail";
        emailService.sendMail(seller.getEmail(), "Account Registered!!", body);
        return true;
    }

    /**
     * Utility functions
     */

    private Address mapSellerAddress(SellerRegistrationDTO sellerRegistrationDTO, Address address) {
        address.setAddressLine(sellerRegistrationDTO.getAddressLine());
        Label label = customValidation.verifyLabel(sellerRegistrationDTO.getLabel());
        address.setLabel(label);
        address.setState(sellerRegistrationDTO.getState());
        address.setCountry(sellerRegistrationDTO.getCountry());
        address.setCity(sellerRegistrationDTO.getCity());
        address.setZipCode(sellerRegistrationDTO.getZipCode());
        return address;
    }

}
