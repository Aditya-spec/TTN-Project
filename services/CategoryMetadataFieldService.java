package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.entities.CategoryMetadataField;
import com.Bootcamp.Project.Application.repositories.CategoryMetadataFieldRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;

@Service
public class CategoryMetadataFieldService {
    @Autowired
    private CategoryMetadataFieldRepository categoryMetadataFieldRepository;
   @Transactional
    public void createCategoryMetadataFieldUsingJson() {
        try {
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("/home/ttn/JsonData/MetaDataFieldData.json"));
            for (Object meta : jsonArray) {
                ObjectMapper mapper = new ObjectMapper();
                CategoryMetadataField categoryMetadataField = mapper.readValue(meta.toString(), CategoryMetadataField.class);
                categoryMetadataFieldRepository.save(categoryMetadataField);
            }
        } catch (Exception e) {
            System.out.println("CategoryMetadata is not added");
            e.printStackTrace();
        }
    }
}
