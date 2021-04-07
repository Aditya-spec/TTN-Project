package com.Bootcamp.Project.Application.controllers;

import com.Bootcamp.Project.Application.dtos.CustomerEmailDTO;
import com.Bootcamp.Project.Application.dtos.CustomerRegistrationDTO;
import com.Bootcamp.Project.Application.dtos.MessageDTO;
import com.Bootcamp.Project.Application.dtos.SellerRegistrationDTO;
import com.Bootcamp.Project.Application.entities.Customer;
import com.Bootcamp.Project.Application.enums.ErrorCode;
import com.Bootcamp.Project.Application.validation.CustomValidation;
import com.Bootcamp.Project.Application.exceptionHandling.EcommerceException;
import com.Bootcamp.Project.Application.repositories.CustomerRepository;
import com.Bootcamp.Project.Application.services.RegistrationImpl;
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
    RegistrationImpl registrationImpl;
    @Autowired
    Customer customer;
    @Autowired
    CustomValidation customValidation;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    MessageDTO messageDTO;


    @PostMapping("/customer")
    public ResponseEntity<MessageDTO> registerCustomer(@Valid @RequestBody CustomerRegistrationDTO customerRegistrationDTO) {
        String result = customValidation.checkCustomerValidation(customerRegistrationDTO);
        if (result != null) {
            messageDTO.setMessage(result);
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_ACCEPTABLE);
        }

        if (registrationImpl.registerCustomer(customerRegistrationDTO)) {
            messageDTO.setMessage("The customer has been registered, please click on the mailed link to activate ");
            return new ResponseEntity<>(messageDTO, HttpStatus.CREATED);
        }
        messageDTO.setMessage("The customer cannot be registered  ");
        return new ResponseEntity<>(messageDTO, HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping("/activate/customer/{token}")
    public ResponseEntity<MessageDTO> customerActivation(@PathVariable String token) {
        if (registrationImpl.findCustomerByToken(token)) {
            if (registrationImpl.activateCustomer(token)) {
                messageDTO.setMessage("customer has been activated");
                return new ResponseEntity<>(messageDTO, HttpStatus.OK);
            } else {
                messageDTO.setMessage("token has been expired, new token has been mailed");
                return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
            }
        }
        throw new EcommerceException(ErrorCode.INVALID_TOKEN);

    }

    @PostMapping("/customer/resendActivationLink")
    public ResponseEntity<MessageDTO> resendActivationToken(@Valid @RequestBody CustomerEmailDTO customerEmailDto) {
        if (registrationImpl.resendActivationLink(customerEmailDto.getEmail())) {
            messageDTO.setMessage("new activation token has been sent via email");
            return new ResponseEntity<>(messageDTO, HttpStatus.OK);
        }
        messageDTO.setMessage("Invalid details entered");
        return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/seller")
    public ResponseEntity<MessageDTO> registerSeller(@Valid @RequestBody SellerRegistrationDTO sellerRegistrationDTO) {
        String result = customValidation.checkSellerValidation(sellerRegistrationDTO);
        if (result != null) {
            messageDTO.setMessage(result);
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_ACCEPTABLE);
        }

        if (registrationImpl.registerSeller(sellerRegistrationDTO)) {
            messageDTO.setMessage("Seller has been registered, please wait for confirmation mail");
            return new ResponseEntity<>(messageDTO, HttpStatus.CREATED);
        }
        messageDTO.setMessage("Seller cannot be registered");
        return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
    }

}
