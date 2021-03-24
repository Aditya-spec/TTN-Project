package com.Bootcamp.Project.Application;

import com.Bootcamp.Project.Application.entities.categoryMetadataFieldValues.CategoryMetadataFieldValues;
import com.Bootcamp.Project.Application.entities.orderProduct.OrderProduct;
import com.Bootcamp.Project.Application.repositories.ProductReviewRepository;
import com.Bootcamp.Project.Application.services.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
       /* UserService userService = applicationContext.getBean(UserService.class);
        for (int i = 0; i < 3; i++) {
            userService.createUsingJson();
        }
        CategoryService categoryService = applicationContext.getBean(CategoryService.class);
        categoryService.createUsingJson();*/

       /* CategoryMetadataFieldService categoryMetadataFieldService = applicationContext.getBean(CategoryMetadataFieldService.class);
        categoryMetadataFieldService.createCategoryMetadataFieldUsingJson();*/

        ProductReviewService productReviewService = applicationContext.getBean(ProductReviewService.class);
        productReviewService.create();

        /*ProductService productService = applicationContext.getBean(ProductService.class);
        productService.createProductUsingJson();*/

        /*CategoryMetadataFieldValuesService categoryMetadataFieldValuesService=applicationContext.getBean(CategoryMetadataFieldValuesService.class);
        categoryMetadataFieldValuesService.createUsingJson();*/

        /*ProductVariationService productVariationService = applicationContext.getBean(ProductVariationService.class);
        productVariationService.createJson();

        InvoiceService invoiceService=applicationContext.getBean(InvoiceService.class);
        invoiceService.createJson();

        OrderProductService orderProductService=applicationContext.getBean(OrderProductService.class);
        orderProductService.create();*/

    /*    CartService cartService = applicationContext.getBean(CartService.class);
        cartService.create();*/

    }
}
