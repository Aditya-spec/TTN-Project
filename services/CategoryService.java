package com.Bootcamp.Project.Application.services;


import com.Bootcamp.Project.Application.entities.Category;
import com.Bootcamp.Project.Application.repositories.CategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;


@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;


    @Transactional
    public void createUsingJson() {
        //ObjectMapper mapper=new ObjectMapper();
        try {
            JSONParser parser = new JSONParser();
            JSONArray categories = (JSONArray) parser.parse(new FileReader("/home/ttn/JsonData/CategoryData.json"));
            for (Object cat : categories) {
                ObjectMapper mapper = new ObjectMapper();
                Category category = mapper.readValue(cat.toString(), Category.class);
                categoryRepository.save(category);
            }
        } catch (Exception e) {
            System.out.println("Category is not added");
            e.printStackTrace();
        }

    }

}
