package com.Bootcamp.Project.Application;

import com.Bootcamp.Project.Application.services.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);

        UserService userService =
                applicationContext.getBean(UserService.class);
        /*userService.createSeller();
        userService.createCustomer();
        userService.createUsingJson();
        userService.createUsingJson();
        for (int i = 0; i < 3; i++) {
            userService.createUsingJson();
        }*/





       /* CategoryService categoryService = applicationContext.getBean(CategoryService.class);
        categoryService.createUsingJson();

           ProductService productService = applicationContext.getBean(ProductService.class);
        productService.createProductUsingJson();*/

        /*CategoryMetadataFieldService categoryMetadataFieldService = applicationContext.getBean(CategoryMetadataFieldService.class);
        categoryMetadataFieldService.createCategoryMetadataFieldUsingJson();*/


     /*CategoryMetadataFieldValuesService categoryMetadataFieldValuesService=applicationContext.getBean(CategoryMetadataFieldValuesService.class);
        categoryMetadataFieldValuesService.createUsingJson();*/


        /*ProductVariationService productVariationService = applicationContext.getBean(ProductVariationService.class);
        productVariationService.createJson();*/

        /*ProductReviewService productReviewService = applicationContext.getBean(ProductReviewService.class);
        productReviewService.create();*/

       /* InvoiceService invoiceService = applicationContext.getBean(InvoiceService.class);
        invoiceService.createJson();*/

        /*OrderProductService orderProductService=applicationContext.getBean(OrderProductService.class);
        orderProductService.create();*/


       /* OrderStatusService orderStatusService = applicationContext.getBean(OrderStatusService.class);
        orderStatusService.create();*/

        /* CartService cartService = applicationContext.getBean(CartService.class);
        cartService.create();*/
    }
}
