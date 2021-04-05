/*
package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.dtos.ProductDTO;
import com.Bootcamp.Project.Application.dtos.ProductVariationDTO;
import com.Bootcamp.Project.Application.dtos.ProductVariationResponseDTO;
import com.Bootcamp.Project.Application.dtos.ProductVariationUpdateDTO;
import com.Bootcamp.Project.Application.entities.*;
import com.Bootcamp.Project.Application.enums.ErrorCode;
import com.Bootcamp.Project.Application.exceptionHandling.EcommerceException;
import com.Bootcamp.Project.Application.repositories.*;
import com.Bootcamp.Project.Application.services.serviceInterfaces.ProductVariationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ProductVariationImpl implements ProductVariationService {
    @Autowired
    private ProductVariationRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ProductVariationRepository productVariationRepository;

    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;

    @Autowired
    CategoryMetadataFieldValuesRepository cmfvRepository;

    @Autowired
    CategoryImpl categoryImpl;

    @Autowired
    ProductImpl productImpl;

    public boolean addVariation(String email, ProductVariationDTO productVariationDTO) {

        Seller seller = sellerRepository.findByEmail(email);
        Product product = productRepository.findById(productVariationDTO.getProductId()).orElse(null);
        if (product == null) {
            throw new EcommerceException(ErrorCode.NO_PRODUCT_FOUND);
        }
        if (seller.getId() != product.getSeller().getId()) {
            throw new EcommerceException(ErrorCode.NOT_AUTHORISED);
        }
        if (!product.getActive()) {
            throw new EcommerceException(ErrorCode.NOT_ACTIVE);
        }
        if (product.getDeleted()) {
            throw new EcommerceException(ErrorCode.NOT_FOUND);
        }
        Category category = categoryRepository.findById(product.getCategory().getId()).orElse(null);
        if (category == null) {
            throw new EcommerceException(ErrorCode.CATEGORY_NOT_EXIST);
        }
        JSONArray inputMetadata = productVariationDTO.getMetadata();
      productImpl.checkVariationDuplicacy(inputMetadata, product);

        ProductVariation productVariation = new ProductVariation();
        productVariation = productImpl.addVariationMapping(productVariation, productVariationDTO, product);
        productVariationRepository.save(productVariation);

        return true;
    }




    public boolean updateVariation(String email, ProductVariationUpdateDTO productVariationUpdateDTO, Long variationId) {
        Seller seller = sellerRepository.findByEmail(email);
        ProductVariation productVariation = productVariationRepository.findById(variationId).orElse(null);
        if (productVariation == null) {
            throw new EcommerceException(ErrorCode.NOT_FOUND);
        }

        Product product = productRepository.findById(productVariation.getProduct().getId()).orElse(null);


        if (seller.getId() != product.getSeller().getId()) {
            throw new EcommerceException(ErrorCode.NOT_AUTHORISED);
        }
        if (!product.getActive()) {
            throw new EcommerceException(ErrorCode.NOT_ACTIVE);
        }

        if (product.getDeleted()) {
            throw new EcommerceException(ErrorCode.NOT_FOUND);
        }
        Category category = categoryRepository.findById(product.getCategory().getId()).orElse(null);
        if (category == null) {
            throw new EcommerceException(ErrorCode.CATEGORY_NOT_EXIST);
        }
        if (productVariationUpdateDTO.getMetadata() != null) {
            JSONArray inputMetadata = productVariationUpdateDTO.getMetadata();
            productImpl.checkVariationDuplicacy(inputMetadata, product);
        }

        productVariation = updateVariationMapping(productVariation, productVariationUpdateDTO);
        productVariationRepository.save(productVariation);
        return true;
    }

    private ProductVariation updateVariationMapping(ProductVariation productVariation, ProductVariationUpdateDTO productVariationUpdateDTO) {
        if (productVariationUpdateDTO.getMetadata() != null) {
            JSONObject metadata = new JSONObject();
            metadata.put("metadata", productVariationUpdateDTO.getMetadata());
            productVariation.setMetadata(metadata);
        }
        if (productVariationUpdateDTO.getPrice() != null) {
            productVariation.setPrice(productVariationUpdateDTO.getPrice());
        }
        if (productVariationUpdateDTO.getActive() != null) {
            productVariation.setActive(productVariationUpdateDTO.getActive());
        }
        if (productVariationUpdateDTO.getPrimaryImageName() != null) {
            productVariation.setPrimaryImageName(productVariationUpdateDTO.getPrimaryImageName());
        }
        if (productVariationUpdateDTO.getQuantityAvailable() != productVariation.getQuantityAvailable()) {
            productVariation.setQuantityAvailable(productVariationUpdateDTO.getQuantityAvailable());
        }

        return productVariation;
    }
    public List<ProductVariationResponseDTO> showProductVariations(String email, Long productId) {
        Seller seller = sellerRepository.findByEmail(email);
        Product product = productRepository.findById(productId).orElse(null);
        if (seller.getId() != product.getSeller().getId()) {
            throw new EcommerceException(ErrorCode.NOT_AUTHORISED);
        }
        if (product.getDeleted()) {
            throw new EcommerceException(ErrorCode.NOT_FOUND);
        }

        List<ProductVariation> variationList = productVariationRepository.fetchVariations(product.getId());
        if (variationList == null) {
            throw new EcommerceException(ErrorCode.NO_DATA);
        }
        List<ProductVariationResponseDTO> productVariationList = new ArrayList<>();
        for (ProductVariation variation : variationList) {
            ProductVariationResponseDTO responseDTO = productImpl.showVariationMapping(product, variation);
            productVariationList.add(responseDTO);
        }
        return productVariationList;
    }

    public ProductVariationResponseDTO showVariation(String email, Long id) {
        ModelMapper modelMapper = new ModelMapper();
        ProductVariation productVariation = productVariationRepository.findById(id).orElse(null);
        if (productVariation == null) {
            throw new EcommerceException(ErrorCode.NO_DATA);
        }
        Product product = productVariation.getProduct();
        Seller seller = product.getSeller();
        ProductVariationResponseDTO responseDTO = productImpl.showVariationMapping(product, productVariation);
        return responseDTO;
    }
    */
/*private ProductVariation addVariationMapping(ProductVariation productVariation, ProductVariationDTO productVariationDTO, Product product) {
        JSONObject metadata = new JSONObject();
        metadata.put("metadata", productVariationDTO.getMetadata());
        productVariation.setMetadata(metadata);

        productVariation.setPrice(productVariationDTO.getPrice());
        productVariation.setPrimaryImageName(productVariationDTO.getPrimaryImageName());
        productVariation.setQuantityAvailable(productVariationDTO.getQuantityAvailable());
        productVariation.setProduct(product);
        product.setActive(true);
        return productVariation;
    }*//*


    */
/*private ProductVariationResponseDTO showVariationMapping(Product product, ProductVariation productVariation) {
        ModelMapper modelMapper = new ModelMapper();
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        ProductVariationResponseDTO responseDTO = new ProductVariationResponseDTO();
        responseDTO.setProductDTO(productDTO);
        responseDTO.setMetadata(productVariation.getMetadata());
        responseDTO.setPrice(productVariation.getPrice());
        responseDTO.setPrimaryImageName(productVariation.getPrimaryImageName());
        responseDTO.setQuantityAvailable(productVariation.getQuantityAvailable());
        responseDTO.setActive(productVariation.getActive());
        responseDTO.setVariationId(productVariation.getId());
        responseDTO.setVariationId(productVariation.getId());
        responseDTO.setProductId(product.getId());
        return responseDTO;
    }*//*



    */
/*private void checkVariationDuplicacy(JSONArray inputMetadata, Product product) {

        for (Object object : inputMetadata) {
            Map<String, Object> metaValues = objectMapper.convertValue(object, Map.class); //Object to JsonObject
            String fieldName = metaValues.get("field").toString();
            String value = metaValues.get("values").toString();

            CategoryMetadataField categoryMetadataField = categoryMetadataFieldRepository.findByname(fieldName).orElse(null);
            if (categoryMetadataField == null) {
                throw new EcommerceException(ErrorCode.NO_METADATAFIELD_EXIST);
            }
            String dbMetaValues = cmfvRepository.fetchMetaDataValues(product.getCategory().getId(), categoryMetadataField.getId());
            if (dbMetaValues == null) {
                throw new EcommerceException(ErrorCode.NOT_FOUND);
            }
            List<String> dbMetaValuesList = Arrays.asList(dbMetaValues.split(","));

            if (!dbMetaValuesList.contains(value)) {
                throw new EcommerceException(ErrorCode.NO_METAVALUES_EXIST);
            }
        }

    }*//*

    }

*/
