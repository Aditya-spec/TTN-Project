package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.entities.Category;
import com.Bootcamp.Project.Application.entities.cart.Cart;
import com.Bootcamp.Project.Application.repositories.CartRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;

@Service
public class CartService {
    @Autowired
private CartRepository repository;
    public void create(){
        try {
            JSONParser parser = new JSONParser();
            JSONArray cartitems = (JSONArray) parser.parse(new FileReader("/home/ttn/JsonData/CartData.json"));
            for (Object cat : cartitems) {
                ObjectMapper mapper = new ObjectMapper();
                Cart cart = mapper.readValue(cat.toString(), Cart.class);
               repository.save(cart);
            }
        } catch (Exception e) {
            System.out.println("Category is not added");
            e.printStackTrace();
        }
    }
}
