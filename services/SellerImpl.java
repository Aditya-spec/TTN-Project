package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.dtos.*;
import com.Bootcamp.Project.Application.entities.Address;
import com.Bootcamp.Project.Application.entities.Seller;
import com.Bootcamp.Project.Application.enums.ErrorCode;
import com.Bootcamp.Project.Application.exceptionHandling.EcommerceException;
import com.Bootcamp.Project.Application.repositories.AddressRepository;
import com.Bootcamp.Project.Application.repositories.SellerRepository;
import com.Bootcamp.Project.Application.services.serviceInterfaces.SellerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class SellerImpl implements SellerService {

    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    MessageDTO messageDTO;

    ModelMapper modelMapper = new ModelMapper();

    public SellerProfileDTO showProfile(String email) {
        ModelMapper modelMapper = new ModelMapper();
        Seller seller = sellerRepository.findByEmail(email);
        if (seller == null) {
            throw new EcommerceException(ErrorCode.USER_NOT_FOUND);
        }
        SellerProfileDTO sellerProfileDto = new SellerProfileDTO();
        sellerProfileDto = mapSeller(seller, sellerProfileDto);
        AddressDTO addressDto = mapAddress(seller.getAddress());
        sellerProfileDto.setAddressDto(addressDto);
        return sellerProfileDto;
    }

    private SellerProfileDTO mapSeller(Seller seller, SellerProfileDTO sellerProfileDTO) {

        sellerProfileDTO.setCompanyName(seller.getCompanyName());
        sellerProfileDTO.setCompanyContact(seller.getCompanyContact());
        sellerProfileDTO.setId(seller.getId());
        sellerProfileDTO.setFirstName(seller.getName().getFirstName());
        sellerProfileDTO.setLastName(seller.getName().getLastName());
        sellerProfileDTO.setMiddleName(seller.getName().getMiddleName());
        sellerProfileDTO.setImagePath(seller.getImagePath());
        sellerProfileDTO.setActive(seller.getActive());

        return sellerProfileDTO;
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

    public boolean sellerResetPassword(String email, PasswordDTO passwordDto) {
        Seller seller = sellerRepository.findByEmail(email);
        if (seller == null || !seller.getActive()) {
            return false;
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        seller.setPassword(passwordEncoder.encode(passwordDto.getPassword()));
        sellerRepository.save(seller);
        String body = "Password has been updated successfully";
        emailService.sendMail(seller.getEmail(), "Password Updated", body);
        return true;
    }


    public ResponseEntity<MessageDTO> updateAddress(String email, Long id, AddressUpdateDTO addressUpdateDto) {
        Address address = addressRepository.getAddressById(id);
        Seller seller = sellerRepository.findByEmail(email);

        if (address == null) {
            messageDTO.setMessage("invalid address id");
            return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
        }

        if (seller.getId() != address.getUser().getId()) {
            throw new EcommerceException(ErrorCode.NOT_AUTHORISED);
        }

        address = mapAddress(address, addressUpdateDto);
        addressRepository.save(address);
        messageDTO.setMessage("Address has been updated successfully");
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
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
        if (addressUpdateDto.getZipCode() != null) {
            address.setZipCode(addressUpdateDto.getZipCode());
        }
        return address;
    }
}

