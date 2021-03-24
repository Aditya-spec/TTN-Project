package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.entities.OrderStatus;
import com.Bootcamp.Project.Application.repositories.OrderStatusRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;

@Service
public class OrderStatusService {
    @Autowired
    OrderStatusRepository orderStatusrepository;

    @Transactional
    public void create(){

        try {
            JSONParser parser = new JSONParser();
            JSONArray categories = (JSONArray) parser.parse(new FileReader("/home/ttn/JsonData/OrderStatusData.json"));
            for (Object cat : categories) {
                ObjectMapper mapper = new ObjectMapper();
                OrderStatus orderStatus = mapper.readValue(cat.toString(), OrderStatus.class);
                orderStatusrepository.save(orderStatus);
            }
        } catch (Exception e) {
            System.out.println("Order Status is not added");
            e.printStackTrace();
        }
    }
}
