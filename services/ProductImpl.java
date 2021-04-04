package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.dtos.ProductVariationDTO;
import com.Bootcamp.Project.Application.dtos.SellerProductAddDTO;
import com.Bootcamp.Project.Application.dtos.SellerProductShowDTO;
import com.Bootcamp.Project.Application.dtos.SellerProductUpdateDTO;
import com.Bootcamp.Project.Application.entities.*;
import com.Bootcamp.Project.Application.enums.ErrorCode;
import com.Bootcamp.Project.Application.exceptionHandling.EcommerceException;
import com.Bootcamp.Project.Application.repositories.*;
import com.Bootcamp.Project.Application.services.serviceInterfaces.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ProductImpl implements ProductService {
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

    Pageable sortById = PageRequest.of(0, 5, Sort.by("id"));

    @Override
    public boolean addProduct(String email, SellerProductAddDTO sellerProductAddDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Seller seller = sellerRepository.findByEmail(email);
        if (seller == null) {
            throw new EcommerceException(ErrorCode.USER_NOT_FOUND);
        }

        Long categoryId = sellerProductAddDTO.getCategory();
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null) {
            throw new EcommerceException(ErrorCode.CATEGORY_NOT_EXIST);
        }

        List<Long> leafCategoriesId = categoryRepository.fetchLeafCategoryId();
        if (!(leafCategoriesId.contains(categoryId))) {
            throw new EcommerceException(ErrorCode.NOT_LEAF_CATEGORY);
        }


        List<Product> productList = productRepository.fetchProduct(sellerProductAddDTO.getBrand(), category.getId(), seller.getId());

        for (Product product : productList) {
            if (product.getName().equalsIgnoreCase(sellerProductAddDTO.getName()) && product.getBrand().equalsIgnoreCase(sellerProductAddDTO.getBrand())) {
                throw new EcommerceException(ErrorCode.NOT_UNIQUE);
            }
        }

        Product newProduct = new Product();
        newProduct = modelMapper.map(sellerProductAddDTO, Product.class);
        newProduct.setDeleted(false);
        category.setProduct(newProduct);
        seller.setProduct(newProduct);

        productRepository.save(newProduct);
        seller.getEmail();
        category.getName();
        newProduct.getBrand();

        String productInfo = newProduct.toString();
        String body = "A new Product has been added by Seller: " + seller.getEmail() + "\nCategory: " + category.getName() + "\nBrand: "
                + newProduct.getBrand() + "please take some action.";
        String topic = "New Product Added!!";
        emailService.sendMail("gauraman32@gmail.com", topic, body);

        return true;
    }

    @Override
    public boolean activateProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return false;
        }
        product.setActive(true);
        productRepository.save(product);
        String body = "Your product = " + product.getName() + " has been  Activated.";
        emailService.sendMail(product.getSeller().getEmail(), "Product Activated", body);
        return true;
    }

    @Override
    public boolean deActivateProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return false;
        }
        product.setActive(false);
        productRepository.save(product);
        String body = "Your product = " + product.getName() + " has been  de-activated.";
        emailService.sendMail(product.getSeller().getEmail(), "Product deActivated", body);
        return true;
    }

    @Override
    public boolean deleteProduct(String email, Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return false;
        }
        Seller seller = sellerRepository.findByEmail(email);
        if (seller.getId() != product.getSeller().getId()) {
            throw new EcommerceException(ErrorCode.NO_PRODUCT_FOUND);
        }
        product.setActive(false);
        product.setDeleted(true);
        productRepository.save(product);
        return true;
    }

    @Override
    public boolean updateProduct(String email, SellerProductUpdateDTO sellerProductUpdateDTO, Long id) {

        Seller seller = sellerRepository.findByEmail(email);

        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new EcommerceException(ErrorCode.NO_PRODUCT_FOUND);
        }

        if (seller.getId() != product.getSeller().getId()) {
            throw new EcommerceException(ErrorCode.NOT_AUTHORISED);
        }


        if (sellerProductUpdateDTO.getName() != null) {
            Product product1 = productRepository.fetchProductForUpdate(seller.getId(), sellerProductUpdateDTO.getName());
            if (product1 != null) {
                throw new EcommerceException(ErrorCode.ALREADY_EXISTS);
            }
            product.setName(sellerProductUpdateDTO.getName());
        }
        if (sellerProductUpdateDTO.getCancellable()) {
            product.setCancellable(sellerProductUpdateDTO.getCancellable());
        }
        if (sellerProductUpdateDTO.getReturnable()) {
            product.setReturnable(sellerProductUpdateDTO.getReturnable());
        }
        if (sellerProductUpdateDTO.getDescription() != null) {
            product.setDescription(sellerProductUpdateDTO.getDescription());
        }

        productRepository.save(product);
        return true;
    }

    @Override
    public SellerProductShowDTO showSellerProduct(String email, Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new EcommerceException(ErrorCode.NO_PRODUCT_FOUND);
        }
        Seller seller = sellerRepository.findByEmail(email);
        if (seller.getId() != product.getSeller().getId()) {
            throw new EcommerceException(ErrorCode.NOT_AUTHORISED);
        }
        if (product.getDeleted()) {
            throw new EcommerceException(ErrorCode.NO_PRODUCT_FOUND);
        }
        SellerProductShowDTO sellerProductShowDTO = new SellerProductShowDTO();
        sellerProductShowDTO = mapping(product, sellerProductShowDTO);
        return sellerProductShowDTO;

    }

    @Override
    public List<SellerProductShowDTO> showAllSellerProducts(String email) {
        Seller seller = sellerRepository.findByEmail(email);
        List<Product> productList = productRepository.fetchBySellerId(seller.getId(), sortById);
        List<SellerProductShowDTO> sellerProductShowDTOList = new ArrayList<>();
        for (Product product : productList) {
            if (!product.getDeleted()) {
                SellerProductShowDTO showDTO = new SellerProductShowDTO();
                showDTO = mapping(product, showDTO);
                sellerProductShowDTOList.add(showDTO);
            }
        }
        return sellerProductShowDTOList;
    }

    @Override
    public boolean addVariation(String email, ProductVariationDTO productVariationDTO) {

        Seller seller = sellerRepository.findByEmail(email);
        Product product = productRepository.findById(productVariationDTO.getProductId()).orElse(null);
        if (product == null) {
            throw new EcommerceException(ErrorCode.NO_PRODUCT_FOUND);
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
        for (Object object : inputMetadata) {
            Map<String, Object> metaValues = objectMapper.convertValue(object, Map.class); //Object to JsonObject
            String fieldName = metaValues.get("fieldName").toString();
            String value = metaValues.get("values").toString();

            CategoryMetadataField categoryMetadataField = categoryMetadataFieldRepository.findByname(fieldName).orElse(null);
            if (categoryMetadataField == null) {
                throw new EcommerceException(ErrorCode.NO_METADATAFIELD_EXIST);
            }
            String dbMetaValues = cmfvRepository.fetchMetaDataValues(category.getId(), categoryMetadataField.getId());
            if (dbMetaValues == null) {
                throw new EcommerceException(ErrorCode.NOT_FOUND);
            }
            List<String> dbMetaValuesList = Arrays.asList(dbMetaValues.split(","));


            if (!dbMetaValuesList.contains(value)) {
                throw new EcommerceException(ErrorCode.NO_METAVALUES_EXIST);
            }
        }
        ProductVariation productVariation = new ProductVariation();
        productVariation = mapping(productVariation, productVariationDTO, product);
        productVariationRepository.save(productVariation);

        return true;

    }

    private ProductVariation mapping(ProductVariation productVariation, ProductVariationDTO productVariationDTO, Product product) {
        JSONObject metadata = new JSONObject();
        metadata.put("metadata", productVariationDTO.getMetadata());
        productVariation.setMetadata(metadata);

        productVariation.setPrice(productVariationDTO.getPrice());
        productVariation.setPrimaryImageName(productVariationDTO.getPrimaryImageName());
        productVariation.setQuantityAvailable(productVariationDTO.getQuantityAvailable());
        productVariation.setProduct(product);
        return productVariation;
    }


    private SellerProductShowDTO mapping(Product product, SellerProductShowDTO showDTO) {
        showDTO.setActive(product.getActive());
        showDTO.setBrand(product.getBrand());
        showDTO.setId(product.getId());
        showDTO.setCategory(product.getCategory().getId());
        showDTO.setDescription(product.getDescription());
        showDTO.setCategoryName(product.getCategory().getName());
        showDTO.setReturnable(product.getReturnable());
        showDTO.setCancellable(product.getCancellable());
        showDTO.setName(product.getName());
        return showDTO;
    }


}