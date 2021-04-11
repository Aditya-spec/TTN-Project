package com.Bootcamp.Project.Application.scheduler;

import com.Bootcamp.Project.Application.entities.Product;
import com.Bootcamp.Project.Application.entities.Seller;
import com.Bootcamp.Project.Application.repositories.ProductRepository;
import com.Bootcamp.Project.Application.repositories.SellerRepository;
import com.Bootcamp.Project.Application.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class EcommerceScheduler {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    EmailService emailService;

    //@Scheduled(cron = "*/10 * * * * *")
    @Scheduled(cron = "@monthly")
    public void scheduledDelete() {
        List<Seller> sellerList = sellerRepository.fetchAllSeller();

        for (Seller seller : sellerList) {
            List<Product> productList = productRepository.fetchBySellerId(seller.getId(), null);
            for (Product product : productList) {
                if (product.getDeleted()) {
                    Date current = new Date();
                    Date currentTime = new Date();
                    long duration = currentTime.getTime() - product.getLastModified().getTime();
                    long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
                    if (diffInMinutes > 1440 * 31)
                        productRepository.delete(product);

                }
            }
        }
    }

    @Scheduled(cron = "@midnight")//("*/5 * * * * *")
    public void scheduledMail() {
        List<Seller> sellerList = sellerRepository.fetchAllSeller();
        List<String> updatedProductList = new ArrayList<>();
        String body = "";
        for (Seller seller : sellerList) {
            if (!seller.getDeleted()) {
                List<Product> productList = productRepository.fetchBySellerIdForScheduler(seller.getId());
                for (Product product : productList) {
                    Date currentTime = new Date();
                    long duration = currentTime.getTime() - product.getLastModified().getTime();
                    long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);

                    if (diffInMinutes < 1440) {
                        String productDescription = "Product name:" + product.getName() + "," + "Brand name:" + product.getBrand() + "\n";
                        updatedProductList.add(productDescription);
                        System.out.println(product.getLastModified().toString());
                    }
                }
                String updatedProducts = String.join("", updatedProductList);
                body = "Here is the list of products you updated today \n" + updatedProducts;
                emailService.sendMail(seller.getEmail(), "Products Updated Today", body);
                //System.out.println(body);
            }
        }

    }

}
