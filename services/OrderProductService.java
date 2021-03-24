package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.entities.Category;
import com.Bootcamp.Project.Application.entities.orderProduct.OrderProduct;
import com.Bootcamp.Project.Application.repositories.OrderProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;

@Service
public class OrderProductService {
    @Autowired
    private OrderProductRepository repository;
public void create(){
    try {
        JSONParser parser = new JSONParser();
        JSONArray categories = (JSONArray) parser.parse(new FileReader("/home/ttn/JsonData/OrderProductData.json"));
        for (Object cat : categories) {
            ObjectMapper mapper = new ObjectMapper();
            OrderProduct orderProduct = mapper.readValue(cat.toString(), OrderProduct.class);
            repository.save(orderProduct);
        }
    } catch (Exception e) {
        System.out.println("OrderProduct is not added");
        e.printStackTrace();
    }
}
}

