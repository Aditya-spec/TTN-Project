package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.entities.Category;
import com.Bootcamp.Project.Application.entities.Customer;
import com.Bootcamp.Project.Application.entities.Product;
import com.Bootcamp.Project.Application.entities.productReview.ProductReview;
import com.Bootcamp.Project.Application.repositories.CustomerRepository;
import com.Bootcamp.Project.Application.repositories.ProductRepository;
import com.Bootcamp.Project.Application.repositories.ProductReviewRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;


@Service
public class ProductReviewService {
    @Autowired
    ProductReviewRepository repository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;

    @Transactional
    public void create(){

        try {
            JSONParser parser = new JSONParser();
            JSONArray categories = (JSONArray) parser.parse(new FileReader("/home/ttn/JsonData/ProductReviewData.json"));
            for (Object cat : categories) {
                ObjectMapper mapper = new ObjectMapper();
                ProductReview productReview = mapper.readValue(cat.toString(), ProductReview.class);
                repository.save(productReview);
            }
        } catch (Exception e) {
            System.out.println("ProductReview is not added");
            e.printStackTrace();
        }
    }
}
