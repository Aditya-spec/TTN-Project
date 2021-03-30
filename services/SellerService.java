package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.dtos.*;
import com.Bootcamp.Project.Application.entities.Address;
import com.Bootcamp.Project.Application.entities.Seller;
import com.Bootcamp.Project.Application.repositories.AddressRepository;
import com.Bootcamp.Project.Application.repositories.SellerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class SellerService {

    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    AddressRepository addressRepository;

    ModelMapper modelMapper = new ModelMapper();

    public SellerProfileDTO showProfile(String email) {
        Seller seller = sellerRepository.findByEmail(email);
        if (seller == null) {
            System.out.println("seller is null");
        }
        SellerProfileDTO sellerProfileDto = mapSeller(seller);
        AddressDTO addressDto = mapAddress(seller.getAddress());
        sellerProfileDto.setAddressDto(addressDto);
        return sellerProfileDto;
    }

    private SellerProfileDTO mapSeller(Seller seller) {
        SellerProfileDTO sellerProfileDto = new SellerProfileDTO();
        sellerProfileDto.setCompanyName(seller.getCompanyName());
        sellerProfileDto.setCompanyContact(seller.getCompanyContact());
        sellerProfileDto.setId(seller.getId());
        sellerProfileDto.setFirstName(seller.getName().getFirstName());
        sellerProfileDto.setLastName(seller.getName().getFirstName());
        sellerProfileDto.setMiddleName(seller.getName().getMiddleName());
        sellerProfileDto.setImagePath(seller.getImagePath());
        sellerProfileDto.setActive(seller.getActive());
        return sellerProfileDto;
    }

    private AddressDTO mapAddress(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setZipCode(address.getZipCode());
        addressDTO.setAddressLine(address.getAddressLine());
        addressDTO.setState(address.getState());
        addressDTO.setLabel(address.getLabel());
        addressDTO.setCountry(address.getCountry());
        addressDTO.setAddressLine(address.getAddressLine());
        addressDTO.setCity(address.getCity());
        return addressDTO;
    }


    public boolean updateSeller(String email, SellerUpdateDTO sellerUpdateDto) {
        Seller seller = sellerRepository.findByEmail(email);
        if (seller == null) {
            return false;
        }
        // seller=modelMapper.map(sellerUpdateDto,Seller.class);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(sellerUpdateDto, seller);
        sellerRepository.save(seller);

        return true;
    }


    public boolean checkPassword(String password, String confirmPassword) {
        if (password.equals(confirmPassword)) {
            return false;
        }
        return true;
    }

    public boolean customerResetPassword(String email, PasswordDTO passwordDto) {
        Seller seller = sellerRepository.findByEmail(email);
        if (seller == null || seller.getActive()) {
            return false;
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        seller.setPassword(passwordEncoder.encode(passwordDto.getPassword()));
        sellerRepository.save(seller);
        String body = "Password has been updated successfully";
        emailService.sendMail(seller.getEmail(), "Password Updated", body);
        return true;
    }


    public ResponseEntity<String> updateAddress(Long id, AddressUpdateDTO addressUpdateDto) {
        Address address = addressRepository.getAddressById(id);
        if (address == null) {
            return new ResponseEntity<>("invalid address id", HttpStatus.BAD_REQUEST);
        }
        address = mapAddress(address, addressUpdateDto);
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
}

