package com.Bootcamp.Project.Application.controllers;

import com.Bootcamp.Project.Application.dtos.CustomerEmailDTO;
import com.Bootcamp.Project.Application.dtos.CustomerRegistrationDTO;
import com.Bootcamp.Project.Application.dtos.SellerRegistrationDTO;
import com.Bootcamp.Project.Application.entities.Customer;
import com.Bootcamp.Project.Application.exceptionHandling.CustomValidation;
import com.Bootcamp.Project.Application.exceptionHandling.NotFoundException;
import com.Bootcamp.Project.Application.repositories.CustomerRepository;
import com.Bootcamp.Project.Application.services.RegistrationService;
import com.Bootcamp.Project.Application.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/register-page")
public class RegistrationController {

    @Autowired
    EmailService emailService;
    @Autowired
    RegistrationService registrationService;
    @Autowired
    Customer customer;
    @Autowired
    CustomValidation customValidation;
    @Autowired
    CustomerRepository customerRepository;


    @PostMapping("/customer")
    public ResponseEntity<String> registerCustomer( @Valid @RequestBody CustomerRegistrationDTO customerRegistrationDTO) {
        String result = customValidation.checkCustomerValidation(customerRegistrationDTO);
        if (result != null) {
            System.out.println(customerRegistrationDTO);
            return new ResponseEntity<>(result, HttpStatus.NOT_ACCEPTABLE);
        }

        if (registrationService.registerCustomer(customerRegistrationDTO)) {
            return new ResponseEntity<>("the customer has been registered, please click on the mailed link to activate ",
                    HttpStatus.CREATED);
        }
        return new ResponseEntity<>("the customer cannot be registered  ",
                HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping("/activate/customer/{token}")
    public ResponseEntity<String> customerActivation(@PathVariable String token) {
        if (registrationService.findCustomerByToken(token)) {
            if (registrationService.activateCustomer(token)) {
                return new ResponseEntity<>("customer has been activated", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("token has been expired, new token has been mailed", HttpStatus.BAD_REQUEST);
            }
        }
        throw new NotFoundException("invalid token");
    }

    @PostMapping("/customer/resendActivationLink")
    public ResponseEntity<String> resendActivationToken(@Valid  @RequestBody CustomerEmailDTO customerEmailDto) {
        if (registrationService.resendActivationLink(customerEmailDto.getEmail())) {
            return new ResponseEntity<>("new activation token has been sent via email", HttpStatus.OK);
        }
        return new ResponseEntity<>("invalid details", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/seller")
    public ResponseEntity<String> registerSeller(@Valid @RequestBody SellerRegistrationDTO sellerRegistrationDTO) {
        String result = customValidation.checkSellerValidation(sellerRegistrationDTO);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.NOT_ACCEPTABLE);
        }
        if (registrationService.registerSeller(sellerRegistrationDTO)) {
            return new ResponseEntity<>("seller has been registered,please wait for confirmation mail", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("seller not created", HttpStatus.BAD_REQUEST);
    }

}
