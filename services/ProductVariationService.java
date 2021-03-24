package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.entities.Category;
import com.Bootcamp.Project.Application.entities.ProductVariation;
import com.Bootcamp.Project.Application.repositories.ProductVariationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;

@Service
public class ProductVariationService {
    @Autowired
    private ProductVariationRepository repository;

    @Transactional
    public void createJson(){
        try {
            JSONParser parser = new JSONParser();
            JSONArray productVariations = (JSONArray) parser.parse(new FileReader("/home/ttn/JsonData/ProductVariationData.json"));
            for (Object cat : productVariations) {
                ObjectMapper mapper = new ObjectMapper();
                ProductVariation productVariation = mapper.readValue(cat.toString(), ProductVariation.class);
                repository.save(productVariation);
            }
        } catch (Exception e) {
            System.out.println("ProductVariation is not added");
            e.printStackTrace();
        }

    }
    }

