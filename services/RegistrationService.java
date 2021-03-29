package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.dtos.CustomerRegistrationDTO;
import com.Bootcamp.Project.Application.dtos.SellerRegistrationDTO;
import com.Bootcamp.Project.Application.entities.Customer;
import com.Bootcamp.Project.Application.entities.Role;
import com.Bootcamp.Project.Application.entities.Seller;
import com.Bootcamp.Project.Application.entities.User;
import com.Bootcamp.Project.Application.repositories.CustomerRepository;
import com.Bootcamp.Project.Application.repositories.RoleRepository;
import com.Bootcamp.Project.Application.repositories.SellerRepository;
import com.Bootcamp.Project.Application.repositories.UserRepository;
import com.Bootcamp.Project.Application.token.ConfirmationToken;
import com.Bootcamp.Project.Application.token.ConfirmationTokenRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.*;

@Service
public class RegistrationService {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SellerRepository sellerRepository;


    ConfirmationToken confirmationToken = null;

    ModelMapper modelMapper = new ModelMapper();

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public Boolean registerCustomer(CustomerRegistrationDTO customerRegistrationDTO) {
        Customer customer = modelMapper.map(customerRegistrationDTO, Customer.class);
        customer.setActive(false);
        String encodedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);

        Role role = roleRepository.findById(4l).get();
        customer.setRoles(Arrays.asList(role));
        customer.setActivationToken(UUID.randomUUID().toString());
        customer.setExpiresAt(LocalTime.now().plusMinutes(15));
        customerRepository.save(customer);

        String body = " Please active your account using this link" +
                " which will be valid for 15 minutes only = \n http://localhost:8080/register-page/activate/customer" + customer.getActivationToken();
        String topic = "Registration Done!!";
        emailService.sendMail(customer.getEmail(), topic, body);
        return true;
    }

    public Boolean activateCustomer(String token) {
        Customer registeredCustomer = customerRepository.findByActivationToken(token);
        LocalTime currentTime = LocalTime.now();
        if (currentTime.isAfter(registeredCustomer.getExpiresAt())) {
            registeredCustomer.setActive(false);
            registeredCustomer.setActivationToken(UUID.randomUUID().toString());
            registeredCustomer.setExpiresAt(LocalTime.now().plusMinutes(15));
            customerRepository.save(registeredCustomer);

            String body = " Please active your account using this link" +
                    " which will be valid for 15 minutes only = \n http://localhost:8080/register-page/activate/customer" + registeredCustomer.getActivationToken();

            emailService.sendMail(registeredCustomer.getEmail(), "Resending activation link", body);
            return false;
        } else {
            System.out.println("activating the customer");
            registeredCustomer.setActive(true);
            registeredCustomer.setActivationToken(null);
            customerRepository.save(registeredCustomer);
            return true;
        }
    }


    public boolean findCustomerByToken(String token) {
        Customer customer = customerRepository.findByActivationToken(token);
        if (customer == null) {
            return false;
        }
        return true;
    }


    @Transactional
    public Boolean resendActivationLink(String email) {

        User user = userRepository.findByEmail(email);
        System.out.println(user);
        if (user == null) {

            return false;
        }

        long id = user.getId();
        Customer customer = customerRepository.findById(id);

        if (customer == null) {
            return false;
        }
        if (customer.getActive()) {
            return false;
        }
        customer.setActivationToken(UUID.randomUUID().toString());
        customer.setExpiresAt(LocalTime.now().plusMinutes(15));
        customerRepository.save(customer);
        String body = " Please active your account using this link" +
                " which will be valid for 15 minutes only = \n http://localhost:8080/register-page/activate/customer" + customer.getActivationToken();
        emailService.sendMail(user.getEmail(), "Resending activation Link", body);
        return true;
    }

    public boolean registerSeller(SellerRegistrationDTO sellerRegistrationDTO) {
        Seller seller=modelMapper.map(sellerRegistrationDTO,Seller.class);
        seller.setActive(false);
        String encodedPassword = passwordEncoder.encode(seller.getPassword());
        seller.setPassword(encodedPassword);
        Role role=roleRepository.findById(2l).get();
        seller.setRoles(Arrays.asList(role));
        sellerRepository.save(seller);
        String body="Your account has been registered, please wait for activation mail";
        emailService.sendMail(seller.getEmail(),"Account registered",body);
        return true;
    }
}
