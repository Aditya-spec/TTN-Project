package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.dtos.AddressDto;
import com.Bootcamp.Project.Application.dtos.PasswordDto;
import com.Bootcamp.Project.Application.dtos.SellerProfileDto;
import com.Bootcamp.Project.Application.entities.Address;
import com.Bootcamp.Project.Application.entities.Customer;
import com.Bootcamp.Project.Application.entities.Seller;
import com.Bootcamp.Project.Application.exceptionHandling.InvalidFieldException;
import com.Bootcamp.Project.Application.repositories.SellerRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.print.attribute.standard.Destination;
import javax.xml.transform.Source;
import java.lang.reflect.Field;
import java.util.Map;

@Service
public class SellerService {

    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    EmailService emailService;

    ModelMapper modelMapper = new ModelMapper();

    public SellerProfileDto showProfile(String email) {
        Seller seller = sellerRepository.findByEmail(email);
        if (seller == null) {
            System.out.println("seller is null");
        }



        SellerProfileDto sellerProfileDto = mapSeller(seller);
        AddressDto addressDto = mapAddress(seller.getAddress());
        sellerProfileDto.setAddressDto(addressDto);
        return sellerProfileDto;
    }

    private SellerProfileDto mapSeller(Seller seller) {
        SellerProfileDto sellerProfileDto = new SellerProfileDto();
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

    private AddressDto mapAddress(Address address) {
        AddressDto addressDTO = new AddressDto();
        addressDTO.setZipCode(address.getZipCode());
        addressDTO.setAddressLine(address.getAddressLine());
        addressDTO.setState(address.getState());
        addressDTO.setLabel(address.getLabel());
        addressDTO.setCountry(address.getCountry());
        addressDTO.setAddressLine(address.getAddressLine());
        addressDTO.setCity(address.getCity());
        return addressDTO;
    }

    public boolean updateSeller(String email, Map<Object, Object> fields) {
        Seller seller = sellerRepository.findByEmail(email);
        if (seller == null) {
            return false;
        }
        try {
            fields.forEach((k, v) -> {
                Field field = ReflectionUtils.findField(Seller.class, (String) k);
                field.setAccessible(true);
                ReflectionUtils.setField(field, seller, v);
            });
        } catch (RuntimeException e) {
            throw new InvalidFieldException("Invalid field");
        }
        sellerRepository.save(seller);
        return true;
    }


        public boolean checkPassword(String password, String confirmPassword) {
            if (password.equals(confirmPassword)) {
                return false;
            }
            return true;
        }

    public boolean customerResetPassword(String email, PasswordDto passwordDto) {
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


}

