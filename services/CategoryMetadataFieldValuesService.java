package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.entities.Category;
import com.Bootcamp.Project.Application.entities.categoryMetadataFieldValues.CategoryMetadataFieldValues;
import com.Bootcamp.Project.Application.repositories.CategoryMetadataFieldValuesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;

@Service
public class CategoryMetadataFieldValuesService {
    @Autowired
    private CategoryMetadataFieldValuesRepository repository;

    @Transactional
    public void createUsingJson() {
        //ObjectMapper mapper=new ObjectMapper();
        try {
            JSONParser parser = new JSONParser();
            JSONArray categories = (JSONArray) parser.parse(new FileReader("/home/ttn/JsonData/CategoryMetaDataFieldValuesData.json"));
            for (Object cat : categories) {
                ObjectMapper mapper = new ObjectMapper();
                CategoryMetadataFieldValues category = mapper.readValue(cat.toString(), CategoryMetadataFieldValues.class);
                repository.save(category);
            }
        } catch (Exception e) {
            System.out.println("Category is not added");
            e.printStackTrace();
        }
    }
}
