package com.Bootcamp.Project.Application.controllers;

import com.Bootcamp.Project.Application.dtos.*;
import com.Bootcamp.Project.Application.services.AdminImpl;
import com.Bootcamp.Project.Application.services.CategoryImpl;
import com.Bootcamp.Project.Application.services.ProductImpl;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminImpl adminImpl;
    @Autowired
    CategoryImpl categoryImpl;
    @Autowired
    ProductImpl productImpl;
    @Autowired
    MessageDTO messageDTO;

    @GetMapping("/home")
    public ResponseEntity<MessageDTO> adminHome() {
        messageDTO.setMessage("Admin home");
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }


    @GetMapping("/get/customers")
    public ResponseEntity<List<RegisteredCustomerDTO>> getAllCustomer(@RequestParam("offset") int offset,
                                                                      @RequestParam("size") int size) {
        return new ResponseEntity<>(adminImpl.getCustomers(offset,size), HttpStatus.OK);
    }


    @GetMapping("/get/sellers")
    public ResponseEntity<List<RegisteredSellerDTO>> getAllSeller(@RequestParam("offset") int offset,
                                                                  @RequestParam("size") int size) {
        return new ResponseEntity<>(adminImpl.getSellers(offset,size), HttpStatus.OK);
    }

    @PatchMapping(path = "/activate-user/{id}")
    public ResponseEntity<MessageDTO> activateUser(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {
        return adminImpl.activateUser(id, fields);
    }


    @PatchMapping("/deactivate-user/{id}")
    public ResponseEntity<MessageDTO> deactivateUser(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {
        return adminImpl.deactivateUser(id, fields);
    }

    @PostMapping("/add-metadataField")
    public ResponseEntity<MessageDTO> addMetadata(@RequestParam String metaDataField) {
        if (metaDataField.equals("")) {
            messageDTO.setMessage("Metadata Field cannot be null");
            return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
        }
        if (metaDataField.length() < 3 || (metaDataField.length() > 15)) {
            messageDTO.setMessage("metadata Field's length must be between 3 to 15 characters");
            return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
        }
        String result = categoryImpl.addMetadata(metaDataField);
        messageDTO.setMessage(result);
        return new ResponseEntity<>(messageDTO, HttpStatus.CREATED);
    }

    @GetMapping("/view-metaDataFields")
    public List<CMDResponseDTO> showMetaDataFields(@RequestParam("offset") int offset,
                                                   @RequestParam("size") int size) {
        return categoryImpl.showMetaData(offset,size);
    }


    @PostMapping("/add-category")
    public ResponseEntity<MessageDTO> addCategory(@Valid @RequestBody CategoryAddDTO categoryAddDTO) {
        String result = categoryImpl.addCategory(categoryAddDTO);
        messageDTO.setMessage(result);
        return new ResponseEntity<>(messageDTO, HttpStatus.CREATED);
    }

    @GetMapping("/view-categories")
    public List<CategoryResponseDTO> viewCategories(@RequestParam("offset") int offset,
                                                    @RequestParam("size") int size) {
        return categoryImpl.showCategories(offset,size);
    }

    @GetMapping("/view-category/{id}")
    public CategoryResponseDTO viewCategory(@PathVariable Long id) {
        return categoryImpl.showCategory(id);
    }

    @PutMapping("/update-category")
    public ResponseEntity<MessageDTO> updateCategory(@RequestParam Long categoryId, @RequestParam String updatedName) {
        String result = categoryImpl.updateCategory(categoryId, updatedName);
        messageDTO.setMessage(result);
        return new ResponseEntity<>(messageDTO, HttpStatus.CREATED);
    }

    @PostMapping("/add-metaValues")
    public ResponseEntity<MessageDTO> addMetaValues(@Valid @RequestBody CategoryMetadataFieldValuesDTO categoryMetadataFieldValuesDTO) {
        String result = categoryImpl.addMetadataValues(categoryMetadataFieldValuesDTO);
        messageDTO.setMessage(result);
        return new ResponseEntity<>(messageDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update-metaValues")
    public ResponseEntity<MessageDTO> update(@Valid @RequestBody CategoryMetadataFieldValuesDTO categoryMetadataFieldValuesDTO) {
        String result = categoryImpl.updateMetadataValues(categoryMetadataFieldValuesDTO);
        messageDTO.setMessage(result);
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

    @PutMapping("/activate-product")
    public ResponseEntity<MessageDTO> activateProduct(@RequestParam Long id) {
        if (productImpl.activateProduct(id)) {
            messageDTO.setMessage("Product has been activated and mail has been sent");
            return new ResponseEntity<>(messageDTO, HttpStatus.OK);
        }
        messageDTO.setMessage("product doesn't exist in the database");
        return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/deActivate-product")
    public ResponseEntity<MessageDTO> deActivateProduct(@RequestParam Long id) {
        if (productImpl.deActivateProduct(id)) {
            messageDTO.setMessage("Product has been deActivated and mail has been sent");
            return new ResponseEntity<>(messageDTO, HttpStatus.OK);
        }
        messageDTO.setMessage("product doesn't exist in the database");
        return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/view-allProducts")
    public MappingJacksonValue viewAllProducts(@RequestParam("offset") int offset,
                                               @RequestParam("size") int size) {
        List<AdminCustomerProductResponseDTO> responseDTOList = productImpl.showAdminAllProducts(offset,size);

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept("productDTO");
        FilterProvider filters = new SimpleFilterProvider().addFilter("responseDTOFilter", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(responseDTOList);
        mapping.setFilters(filters);
        return mapping;
    }

    @GetMapping("/view-product")
    public MappingJacksonValue viewProduct(@RequestParam Long id) {
        AdminCustomerProductResponseDTO responseDTO = productImpl.showAdminProduct(id);


        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept("productDTO");
        FilterProvider filters = new SimpleFilterProvider().addFilter("responseDTOFilter", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(responseDTO);
        mapping.setFilters(filters);
        return mapping;

    }
}








