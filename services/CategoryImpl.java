package com.Bootcamp.Project.Application.services;


import com.Bootcamp.Project.Application.dtos.CategoryAddDTO;
import com.Bootcamp.Project.Application.dtos.CategoryMetadataDTO;
import com.Bootcamp.Project.Application.dtos.CategoryResponseDTO;
import com.Bootcamp.Project.Application.entities.Category;
import com.Bootcamp.Project.Application.entities.CategoryMetadataField;
import com.Bootcamp.Project.Application.enums.ErrorCode;
import com.Bootcamp.Project.Application.exceptionHandling.EcommerceException;
import com.Bootcamp.Project.Application.repositories.CategoryMetadataFieldRepository;
import com.Bootcamp.Project.Application.repositories.CategoryRepository;
import com.Bootcamp.Project.Application.services.serviceInterfaces.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CategoryImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    CategoryMetadataFieldRepository metadataFieldRepository;


    Pageable sortById = PageRequest.of(0, 5, Sort.by("id"));

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

    @Override
    public String addCategory(CategoryAddDTO categoryAddDTO) {
        Category category = categoryRepository.findByName(categoryAddDTO.getName());
        if (category != null) {
            throw new EcommerceException(ErrorCode.ALREADY_EXISTS);
        }
        Category newCategory = new Category();
        if (categoryAddDTO.getParentId() == null) {
            newCategory.setName(categoryAddDTO.getName());
            newCategory.setParentId(0l);
            categoryRepository.save(newCategory);
            return "Category added successfully";
        } else {
            Optional<Category> parentCategory = categoryRepository.findById(categoryAddDTO.getParentId());
            if (parentCategory.isEmpty())
                throw new EcommerceException(ErrorCode.PARENT_CATEGORY_NOT_EXISTS);
            else {
                newCategory.setName(categoryAddDTO.getName());
                newCategory.setParentId(categoryAddDTO.getParentId());
                categoryRepository.save(newCategory);
                return "Category added successfully";
            }
        }
    }

    @Override
    public List<CategoryResponseDTO> showCategories() {
        ModelMapper modelMapper = new ModelMapper();
        Optional<List<Category>> categories = categoryRepository.fetchALlCategories(sortById);
        if (categories.isEmpty()) {
            throw new EcommerceException(ErrorCode.NO_DATA);
        }

       /* List<Category> categoryList = categories.get();
        List<CategoryAddDTO> categoryAddDTOList = categoryList.stream().map(e -> modelMapper.map(e, CategoryAddDTO.class)).collect(Collectors.toList());
        */
        return null;
    }

    @Override
    public CategoryResponseDTO showCategory(Long id) {
        ModelMapper modelMapper = new ModelMapper();
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new EcommerceException(ErrorCode.NOT_FOUND);
        }
        List<CategoryAddDTO> categoryAddDTOList = new ArrayList<>();
        List<CategoryAddDTO> childrenCategory = findCategoryChildren(id);
        categoryAddDTOList.addAll(childrenCategory);

        List<CategoryAddDTO> parentCategoryAddDTOList = new ArrayList<>();
        List<CategoryAddDTO> parentCategory = findCategoryParent(id);
        parentCategoryAddDTOList.addAll(parentCategory);

        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setName(category.get().getName());
        categoryResponseDTO.setParentId(category.get().getParentId());
        categoryResponseDTO.setChildCategory(categoryAddDTOList);
        categoryResponseDTO.setParentCategory(parentCategoryAddDTOList);
        return categoryResponseDTO;
    }

    private List<CategoryAddDTO> findCategoryParent(Long id) {
        ModelMapper modelMapper = new ModelMapper();
        Category category = categoryRepository.findById(id).get();
        List<CategoryAddDTO> categoryAddDTOList = new ArrayList<>();
        while (category.getParentId() != 0l) {
            Optional<Category> category1 = categoryRepository.findById(category.getParentId());
            categoryAddDTOList.add(modelMapper.map(category1.get(), CategoryAddDTO.class));
            category = category1.get();
        }
        return categoryAddDTOList;
    }


    private List<CategoryAddDTO> findCategoryChildren(Long id) {
        ModelMapper modelMapper = new ModelMapper();
        Optional<List<Category>> categories = categoryRepository.findNextChildren(id);
        if (categories.isEmpty()) {
            return null;
        }
        List<Category> categoryList = categories.get();
        List<CategoryAddDTO> categoryAddDTOList = categoryList.stream().map(e -> modelMapper.map(e, CategoryAddDTO.class)).collect(Collectors.toList());
        return categoryAddDTOList;
    }


    @Override
    public String addMetadata(String metaData) {
        ModelMapper modelMapper = new ModelMapper();
        Optional<CategoryMetadataField> metadataField = metadataFieldRepository.findByname(metaData);
        if (metadataField.isPresent()) {
            throw new EcommerceException(ErrorCode.ALREADY_EXISTS);
        }
        // CategoryMetadataField categoryMetadataField = metadataField.get();
        CategoryMetadataField categoryMetadataField = new CategoryMetadataField();
        //CategoryMetadataDTO categoryMetadataDTO=modelMapper.map(metadataField,CategoryMetadataDTO.class);
        categoryMetadataField.setName(metaData);
        // categoryMetadataField.setCategoryMetadataFieldValuesSet(metadataField.get().getCategoryMetadataFieldValuesSet());
        metadataFieldRepository.save(categoryMetadataField);
        return "Metadata added successfully";
    }

    @Override
    public List<CategoryMetadataDTO> showMetaData() {
        ModelMapper modelMapper = new ModelMapper();
        Optional<List<CategoryMetadataField>> metadataFieldList = metadataFieldRepository.fetchAll();
        if (metadataFieldList.isEmpty()) {
            throw new EcommerceException(ErrorCode.NO_DATA);
        }
        List<CategoryMetadataField> categoryMetadataFieldList = metadataFieldList.get();
        List<CategoryMetadataDTO> categoryMetadataFieldDTOS = categoryMetadataFieldList
                .stream()
                .map(e -> modelMapper.map(e, CategoryMetadataDTO.class))
                .collect(Collectors.toList());
        return categoryMetadataFieldDTOS;
    }


}
