package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.dtos.*;
import com.Bootcamp.Project.Application.entities.Address;
import com.Bootcamp.Project.Application.entities.Customer;
import com.Bootcamp.Project.Application.entities.Name;
import com.Bootcamp.Project.Application.enums.ErrorCode;
import com.Bootcamp.Project.Application.enums.Label;
import com.Bootcamp.Project.Application.exceptionHandling.EcommerceException;
import com.Bootcamp.Project.Application.repositories.AddressRepository;
import com.Bootcamp.Project.Application.repositories.CustomerRepository;
import com.Bootcamp.Project.Application.services.serviceInterfaces.CustomerService;
import com.Bootcamp.Project.Application.validations.CustomValidation;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    MessageDTO messageDTO;
    @Autowired
    CustomValidation customValidation;
    @Autowired
    PaginationImpl paginationImpl;

    ModelMapper modelMapper = new ModelMapper();
    Logger logger = LoggerFactory.getLogger(getClass().getName());

    public CustomerProfileDTO showProfile(String email) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            throw new EcommerceException(ErrorCode.USER_NOT_FOUND);
        }
        CustomerProfileDTO customerProfileDto = modelMapper.map(customer, CustomerProfileDTO.class);
        return customerProfileDto;
    }

    public List<AddressDTO> showAddresses(String email) {

        Customer customer = customerRepository.findByEmail(email);
        List<Address> addressList = addressRepository.fetchAddresses(customer.getId());
        if (addressList.size() == 0) {
            throw new EcommerceException(ErrorCode.ADDRESS_NOT_FOUND);
        }
        List<AddressDTO> showAddressDTOList = addressList.stream().map(e -> mapAddressToDTO(e)).collect(Collectors.toList());

        return showAddressDTOList;
    }

    public Boolean addAddress(String email, AddressDTO addressDTO) {

        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            throw new EcommerceException(ErrorCode.USER_NOT_FOUND);
        }
        Address address = mapAddressFromDTO(addressDTO);

        customer.setAddress(address);
        customerRepository.save(customer);

        return true;
    }

    public boolean deleteAddress(Long id, String email) {
        Customer customer = customerRepository.findByEmail(email);
        Address address = addressRepository.findById(id).orElse(null);
        if (address == null) {
            throw new EcommerceException(ErrorCode.ADDRESS_NOT_FOUND);
        }
        if (customer.getId() != address.getUser().getId()) {
            throw new EcommerceException(ErrorCode.NOT_AUTHORISED);
        }
        address.setDeleted(true);
        addressRepository.save(address);
        return true;
    }

    public boolean updateProfile(String email, CustomerProfileDTO customerProfileDto) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            return false;
        }
        customer = mapCustomerFromDTO(customerProfileDto, customer);
        customerRepository.save(customer);
        return true;
    }

    public boolean checkPassword(String password, String confirmPassword) {
        if (password.equals(confirmPassword)) {
            return false;
        }
        return true;
    }


    public boolean customerResetPassword(String email, PasswordDTO passwordDto) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null || !customer.getActive()) {
            return false;
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        customer.setPassword(passwordEncoder.encode(passwordDto.getPassword()));
        customerRepository.save(customer);
        String body = "Password has been updated successfully";
        emailService.sendMail(customer.getEmail(), "Password Updated", body);
        logger.info(body);
        return true;
    }

    public ResponseEntity<MessageDTO> updateAddress(String email, Long addressId, AddressUpdateDTO addressUpdateDto) {
        Customer customer = customerRepository.findByEmail(email);
        Address address = addressRepository.getAddressById(addressId);
        if (address == null) {
            messageDTO.setMessage("invalid address id");
            return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
        }
        if (customer.getId() != address.getUser().getId()) {
            throw new EcommerceException(ErrorCode.NOT_AUTHORISED);
        }
        address = mapUpdateAddressFromDTO(address, addressUpdateDto);

        addressRepository.save(address);

        messageDTO.setMessage("Address has been updated successfully");
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }


    /**
     * Utilities Function
     */

    private Customer mapCustomerFromDTO(CustomerProfileDTO customerProfileDto, Customer customer) {
        Name name = customer.getName();
        if (customerProfileDto.getFirstName() != null) {
            name.setFirstName(customerProfileDto.getFirstName());
        }
        if (customerProfileDto.getLastName() != null) {
            name.setLastName(customerProfileDto.getLastName());
        }

        if (customerProfileDto.getMiddleName() != null) {
            name.setMiddleName(customerProfileDto.getMiddleName());
        }
        customer.setName(name);

        if (customerProfileDto.getContact() != null) {
            customer.setContact(customerProfileDto.getContact());
        }

        if (customerProfileDto.getImagePath() != null) {
            customer.setImagePath(customerProfileDto.getImagePath());
        }
        return customer;
    }

    private Address mapUpdateAddressFromDTO(Address address, AddressUpdateDTO addressUpdateDto) {
        if (addressUpdateDto.getAddressLine() != null) {
            address.setAddressLine(addressUpdateDto.getAddressLine());
        }
        if (addressUpdateDto.getCity() != null) {
            address.setCity(addressUpdateDto.getCity());
        }
        if (addressUpdateDto.getState() != null) {
            address.setState(addressUpdateDto.getState());
        }
        if (addressUpdateDto.getCountry() != null) {
            address.setCountry(addressUpdateDto.getCountry());
        }
        if (addressUpdateDto.getLabel() != null) {
            Label label = customValidation.verifyLabel(addressUpdateDto.getLabel());
            address.setLabel(label);
        }
        if (addressUpdateDto.getZipCode() != null) {
            address.setZipCode(addressUpdateDto.getZipCode());
        }
        return address;
    }

    private Address mapAddressFromDTO(AddressDTO addressDTO) {
        Address address = new Address();
        address.setAddressLine(addressDTO.getAddressLine());
        Label label = customValidation.verifyLabel(addressDTO.getLabel().toString());
        address.setLabel(label);
        address.setCity(addressDTO.getCity());
        address.setCountry(addressDTO.getCountry());
        address.setState(addressDTO.getState());
        address.setZipCode(addressDTO.getZipCode());
        return address;
    }

    public AddressDTO mapAddressToDTO(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setAddressLine(address.getAddressLine());
        addressDTO.setLabel(address.getLabel().toString());
        addressDTO.setCity(address.getCity());
        addressDTO.setCountry(address.getCountry());
        addressDTO.setState(address.getState());
        addressDTO.setZipCode(address.getZipCode());
        return addressDTO;
    }

}