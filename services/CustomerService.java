package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.dtos.PasswordDto;
import com.Bootcamp.Project.Application.dtos.RegisteredCustomerDto;
import com.Bootcamp.Project.Application.dtos.ShowAddressDto;
import com.Bootcamp.Project.Application.entities.Address;
import com.Bootcamp.Project.Application.entities.Customer;
import com.Bootcamp.Project.Application.exceptionHandling.InvalidFieldException;
import com.Bootcamp.Project.Application.exceptionHandling.NotFoundException;
import com.Bootcamp.Project.Application.repositories.AddressRepository;
import com.Bootcamp.Project.Application.repositories.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    EmailService emailService;

    ModelMapper modelMapper = new ModelMapper();

    public RegisteredCustomerDto showProfile(String email) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            throw new NotFoundException("no customer for this email exists");
        }
        RegisteredCustomerDto registeredCustomerDto = modelMapper.map(customer, RegisteredCustomerDto.class);
        return registeredCustomerDto;
    }

    public List<ShowAddressDto> showAddresses(String email) {
        Customer customer = customerRepository.findByEmail(email);
        List<Address> addressList = addressRepository.fetchAddresses(customer.getId());
        if (addressList == null) {
            throw new NotFoundException("No Address is stored");
        }
        Type setType = new TypeToken<List<ShowAddressDto>>() {
        }.getType();
        List<ShowAddressDto> showAddressDtoList = modelMapper.map(addressList, setType);
        return showAddressDtoList;
    }

    public Boolean addAddress(String email, ShowAddressDto showAddressDto) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            throw new NotFoundException("no customer for this email exists");
        }
        Address address = modelMapper.map(showAddressDto, Address.class);
        customer.getAddressList().add(address);
        return true;
    }

    public boolean deleteAddress(Long id) {
        Address address = addressRepository.findById(id).orElse(null);
        if (address == null) {
            throw new NotFoundException("address doesn't exist");
        }
        address.setDeleted(true);
        addressRepository.save(address);
        return true;


    }

    public Boolean updateProfile(String email, Map<Object, Object> fields) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            return false;
        }
        try {
            fields.forEach((k, v) -> {
                Field field = ReflectionUtils.findField(Customer.class, (String) k);
                field.setAccessible(true);
                ReflectionUtils.setField(field, customer, v);
            });
        } catch (RuntimeException e) {
            throw new InvalidFieldException("Invalid field");
        }
        customerRepository.save(customer);
        return true;
    }

    public boolean checkPassword(String password, String confirmPassword) {
        if (password.equals(confirmPassword)) {
            return false;
        }
        return true;
    }


    public boolean customerResetPassword(String email, PasswordDto passwordDto) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            return false;
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        customer.setPassword(passwordEncoder.encode(passwordDto.getPassword()));
        customerRepository.save(customer);
        String body = "Password has been updated successfully";
        emailService.sendMail(customer.getEmail(), "Password Updated", body);
        return true;
    }

    public ResponseEntity<String> updateAddress(Long id, Map<Object, Object> fields) {
        Address address = addressRepository.getAddressById(id);
        if (address == null) {
            return new ResponseEntity<>("invalid address id", HttpStatus.BAD_REQUEST);
        }
        try {
            fields.forEach((k, v) -> {
                Field field = ReflectionUtils.findField(Address.class, (String) k);
                field.setAccessible(true);
                ReflectionUtils.setField(field, address, v);
            });
        } catch (RuntimeException e) {
            throw new InvalidFieldException("Invalid field ");
        }
        addressRepository.save(address);
        return new ResponseEntity<>("Address has been updated successfully", HttpStatus.OK);

    }
}