package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.entities.Category;
import com.Bootcamp.Project.Application.entities.ProductVariation;
import com.Bootcamp.Project.Application.repositories.ProductVariationRepository;
import com.Bootcamp.Project.Application.services.serviceInterfaces.ProductVariationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;

@Service
public class ProductVariationImpl implements ProductVariationService {
    @Autowired
    private ProductVariationRepository repository;


    }

