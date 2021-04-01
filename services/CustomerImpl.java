package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.dtos.*;
import com.Bootcamp.Project.Application.entities.Address;
import com.Bootcamp.Project.Application.entities.Customer;
import com.Bootcamp.Project.Application.entities.Name;
import com.Bootcamp.Project.Application.enums.ErrorCode;
import com.Bootcamp.Project.Application.exceptionHandling.EcommerceException;
import com.Bootcamp.Project.Application.repositories.AddressRepository;
import com.Bootcamp.Project.Application.repositories.CustomerRepository;
import com.Bootcamp.Project.Application.services.serviceInterfaces.CustomerService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class CustomerImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    EmailService emailService;

    ModelMapper modelMapper = new ModelMapper();

    public CustomerProfileDTO showProfile(String email) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            throw new EcommerceException(ErrorCode.USER_NOT_FOUND);
            //throw new NotFoundException("no customer for this email exists");
        }
        CustomerProfileDTO customerProfileDto = modelMapper.map(customer, CustomerProfileDTO.class);
        return customerProfileDto;
    }

    public List<AddressDTO> showAddresses(String email) {
        Customer customer = customerRepository.findByEmail(email);
        List<Address> addressList = addressRepository.fetchAddresses(customer.getId());
        if (addressList == null) {
            throw new EcommerceException(ErrorCode.ADDRESS_NOT_FOUND);
            //throw new NotFoundException("No Address is stored");
        }
        Type setType = new TypeToken<List<AddressDTO>>() {
        }.getType();
        List<AddressDTO> showAddressDTOList = modelMapper.map(addressList, setType);
        return showAddressDTOList;
    }

    public Boolean addAddress(String email, AddressDTO addressDTO) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
           throw new EcommerceException(ErrorCode.USER_NOT_FOUND);
            // throw new NotFoundException("no customer for this email exists");
        }
        Address address = modelMapper.map(addressDTO, Address.class);
        customer.setAddress(address);
        customerRepository.save(customer);
       /* addressRepository.save(address);*/
        return true;
    }

    public boolean deleteAddress(Long id) {
        Address address = addressRepository.findById(id).orElse(null);
        if (address == null) {
           throw new EcommerceException(ErrorCode.ADDRESS_NOT_FOUND);
            // throw new NotFoundException("address doesn't exist");
        }
        address.setDeleted(true);
        addressRepository.save(address);
        return true;


    }


    /*public boolean updateProfile(String email, Map<Object, Object> fields) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            return false;
        }
        CustomerProfileDTO customerProfileDto=modelMapper.map(customer,CustomerProfileDTO.class);
        try {
            fields.forEach((k, v) -> {
                Field field = ReflectionUtils.findField(CustomerProfileDTO.class, (String) k);
                field.setAccessible(true);
                ReflectionUtils.setField(field, customerProfileDto, v);
            });
        } catch (RuntimeException e) {
            throw new InvalidFieldException("Invalid field");
        }

        customer=modelMapper.map(customerProfileDto,Customer.class);
        customerRepository.save(customer);
        return true;
    }*/

    public boolean updateProfile(String email, CustomerProfileDTO customerProfileDto) {
        Customer customer=customerRepository.findByEmail(email);
        if (customer == null) {
            return false;
        }
       customer=mapCustomer(customerProfileDto,customer);
        customerRepository.save(customer);
        return true;
    }
    private Customer mapCustomer(CustomerProfileDTO customerProfileDto, Customer customer) {
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


    public boolean checkPassword(String password, String confirmPassword) {
        if (password.equals(confirmPassword)) {
            return false;
        }
        return true;
    }


    public boolean customerResetPassword(String email, PasswordDTO passwordDto) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null || customer.getActive()) {
            return false;
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        customer.setPassword(passwordEncoder.encode(passwordDto.getPassword()));
        customerRepository.save(customer);
        String body = "Password has been updated successfully";
        emailService.sendMail(customer.getEmail(), "Password Updated", body);
        return true;
    }

    /*public ResponseEntity<String> updateAddress(Long id, Map<Object, Object> fields) {
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
    }*/

    public ResponseEntity<String> updateAddress(Long id, AddressUpdateDTO addressUpdateDto) {
        Address address = addressRepository.getAddressById(id);
        if (address == null) {
            return new ResponseEntity<>("invalid address id", HttpStatus.BAD_REQUEST);
        }
        address=mapAddress(address,addressUpdateDto);

        addressRepository.save(address);

        return new ResponseEntity<>("Address has been updated successfully", HttpStatus.OK);
    }
    private Address mapAddress(Address address, AddressUpdateDTO addressUpdateDto) {
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
            address.setLabel(addressUpdateDto.getLabel());
        }
        if (addressUpdateDto.getZipCode() != 0) {
            address.setZipCode(addressUpdateDto.getZipCode());
        }
        return address;
    }
   /* mapAddress(targetAddress.get(), addressDTO);*/


}