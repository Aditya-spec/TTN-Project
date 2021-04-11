package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.dtos.*;
import com.Bootcamp.Project.Application.entities.Address;
import com.Bootcamp.Project.Application.entities.Seller;
import com.Bootcamp.Project.Application.enums.ErrorCode;
import com.Bootcamp.Project.Application.enums.Label;
import com.Bootcamp.Project.Application.exceptionHandling.EcommerceException;
import com.Bootcamp.Project.Application.repositories.AddressRepository;
import com.Bootcamp.Project.Application.repositories.SellerRepository;
import com.Bootcamp.Project.Application.services.serviceInterfaces.SellerService;
import com.Bootcamp.Project.Application.validations.CustomValidation;
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
    @Autowired
    CustomValidation customValidation;

    ModelMapper modelMapper = new ModelMapper();

    public SellerProfileDTO showProfile(String email) {

        Seller seller = sellerRepository.findByEmail(email);
        if (seller == null) {
            throw new EcommerceException(ErrorCode.USER_NOT_FOUND);
        }
        SellerProfileDTO sellerProfileDto = new SellerProfileDTO();
        sellerProfileDto = mapSellerToDTO(seller, sellerProfileDto);
        sellerProfileDto = mapAddressToDTO(seller.getAddress(), sellerProfileDto);

        return sellerProfileDto;
    }


    public boolean updateSeller(String email, SellerUpdateDTO sellerUpdateDto) {
        Seller seller = sellerRepository.findByEmail(email);
        if (seller == null) {
            return false;
        }

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

        address = mapUpdatedAddressFromDTO(address, addressUpdateDto);
        addressRepository.save(address);
        messageDTO.setMessage("Address has been updated successfully");
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

    /**
     * Utility functions
     */

    private Address mapUpdatedAddressFromDTO(Address address, AddressUpdateDTO addressUpdateDto) {
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

    private SellerProfileDTO mapSellerToDTO(Seller seller, SellerProfileDTO sellerProfileDTO) {

        sellerProfileDTO.setCompanyName(seller.getCompanyName());
        sellerProfileDTO.setCompanyContact(seller.getCompanyContact());
        sellerProfileDTO.setId(seller.getId());
        sellerProfileDTO.setFirstName(seller.getName().getFirstName());
        sellerProfileDTO.setLastName(seller.getName().getLastName());
        sellerProfileDTO.setMiddleName(seller.getName().getMiddleName());
        sellerProfileDTO.setImagePath(seller.getImagePath());
        sellerProfileDTO.setActive(seller.getActive());
        sellerProfileDTO.setEmail(seller.getEmail());

        return sellerProfileDTO;
    }

    private SellerProfileDTO mapAddressToDTO(Address address, SellerProfileDTO sellerProfileDTO) {

        sellerProfileDTO.setZipCode(address.getZipCode());
        sellerProfileDTO.setAddressLine(address.getAddressLine());
        sellerProfileDTO.setState(address.getState());
        sellerProfileDTO.setLabel(address.getLabel().toString());
        sellerProfileDTO.setCountry(address.getCountry());
        sellerProfileDTO.setAddressLine(address.getAddressLine());
        sellerProfileDTO.setCity(address.getCity());
        return sellerProfileDTO;
    }
}

