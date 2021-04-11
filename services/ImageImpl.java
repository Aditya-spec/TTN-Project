package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.dtos.MessageDTO;
import com.Bootcamp.Project.Application.entities.ProductVariation;
import com.Bootcamp.Project.Application.entities.Seller;
import com.Bootcamp.Project.Application.entities.User;
import com.Bootcamp.Project.Application.enums.ErrorCode;
import com.Bootcamp.Project.Application.exceptionHandling.EcommerceException;
import com.Bootcamp.Project.Application.repositories.ProductRepository;
import com.Bootcamp.Project.Application.repositories.ProductVariationRepository;
import com.Bootcamp.Project.Application.repositories.SellerRepository;
import com.Bootcamp.Project.Application.repositories.UserRepository;
import com.Bootcamp.Project.Application.services.serviceInterfaces.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.EOFException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Service
public class ImageImpl implements ImageService {

    @Autowired
    MessageDTO messageDTO;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    ProductVariationRepository productVariationRepository;
    final String IMAGE_DIR = "/home/ttn/Ecommerce_Images/";

    @Override
    public ResponseEntity<MessageDTO> uploadImage(MultipartFile image, String email) throws IOException {
        if (image.isEmpty()) {
            throw new IOException();
        }
        User user = userRepository.findByEmail(email);
        String fileName = image.getOriginalFilename();
        byte[] bytes = image.getBytes();
        Path path = Paths.get(IMAGE_DIR + fileName);
        Files.write(path, bytes);
        saveFile(path.toString(), user);
        messageDTO.setMessage("Image successfully uploaded");

        return new ResponseEntity<>(messageDTO, HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<MessageDTO> uploadVariationImage(MultipartFile image, long variationId, String email) throws IOException {

        Seller seller = sellerRepository.findByEmail(email);
        ProductVariation productVariation = productVariationRepository.findById(variationId).orElse(null);
        if(productVariation==null){
            throw new EcommerceException(ErrorCode.NO_VARIATION_FOUND);
        }
        if(seller.getId()!=productVariation.getProduct().getSeller().getId()){
            throw new EcommerceException(ErrorCode.NOT_AUTHORISED);
        }

        if (image.isEmpty()) {
            throw new IOException();
        }
        String fileName = image.getOriginalFilename();
        byte[] bytes = image.getBytes();
        Path path = Paths.get(IMAGE_DIR + fileName);
        Files.write(path, bytes);
        saveImage(path.toString(), productVariation);
        messageDTO.setMessage("Image successfully uploaded");
        return new ResponseEntity<>(messageDTO, HttpStatus.ACCEPTED);
    }

    private void saveImage(String imagePath, ProductVariation productVariation) {
        productVariation.setPrimaryImageName(imagePath);
        productVariationRepository.save(productVariation);
    }


    public void saveFile(String filename, User user) {
        user.setImagePath(filename);
        userRepository.save(user);
    }
    }

