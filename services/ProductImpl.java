package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.dtos.SellerProductAddDTO;
import com.Bootcamp.Project.Application.entities.Category;
import com.Bootcamp.Project.Application.entities.Product;
import com.Bootcamp.Project.Application.entities.Seller;
import com.Bootcamp.Project.Application.enums.ErrorCode;
import com.Bootcamp.Project.Application.exceptionHandling.EcommerceException;
import com.Bootcamp.Project.Application.repositories.CategoryRepository;
import com.Bootcamp.Project.Application.repositories.ProductRepository;
import com.Bootcamp.Project.Application.repositories.SellerRepository;
import com.Bootcamp.Project.Application.services.serviceInterfaces.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

        Product product = productRepository.findByName(sellerProductAddDTO.getName());
        if (product != null) {
            return false;
        }

        product = modelMapper.map(sellerProductAddDTO, Product.class);
        product.setDeleted(false);
        category.setProduct(product);
        seller.setProduct(product);

        productRepository.save(product);
        seller.getEmail();
        category.getName();
        product.getBrand();

        String productInfo = product.toString();
        String body = "A new Product has been added by Seller: " + seller.getEmail() + "\nCategory: " + category.getName() + "\nBrand: "
                + product.getBrand() + "please take some action.";
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
        Seller seller=sellerRepository.findByEmail(email);
        if (seller.getId()!=product.getSeller().getId()){
            throw new EcommerceException(ErrorCode.NO_PRODUCT_FOUND);
        }
        product.setActive(false);
        product.setDeleted(true);
        productRepository.save(product);
        return true;
    }
}
