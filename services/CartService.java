package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.entities.Cart;
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

    public void create() {
        try {
            JSONParser parser = new JSONParser();
            JSONArray cartItems = (JSONArray) parser.parse(new FileReader("/home/ttn/JsonData/CartData.json"));
            for (Object cat : cartItems) {
                ObjectMapper mapper = new ObjectMapper();
                Cart cart = mapper.readValue(cat.toString(), Cart.class);
                repository.save(cart);
            }
        } catch (Exception e) {
            System.out.println("Cart is not added");
            e.printStackTrace();
        }
    }
}
