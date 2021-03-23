package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.entities.Category;
import com.Bootcamp.Project.Application.entities.Product;
import com.Bootcamp.Project.Application.repositories.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
public void createProductUsingJson(){
    try {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("/home/ttn/JsonData/ProductData.json"));
        for (Object object : jsonArray) {
            ObjectMapper mapper = new ObjectMapper();
            Product product= mapper.readValue(object.toString(), Product.class);
            productRepository.save(product);
        }
    } catch (Exception e) {
        System.out.println("Product is not added");
        e.printStackTrace();
    }
}
}
