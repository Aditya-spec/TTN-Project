package com.Bootcamp.Project.Application.services;


import com.Bootcamp.Project.Application.dtos.*;
import com.Bootcamp.Project.Application.entities.Category;
import com.Bootcamp.Project.Application.entities.CategoryMetadataField;
import com.Bootcamp.Project.Application.entities.categoryMetadataFieldValues.CategoryMetadataFieldValues;
import com.Bootcamp.Project.Application.enums.ErrorCode;
import com.Bootcamp.Project.Application.exceptionHandling.EcommerceException;
import com.Bootcamp.Project.Application.repositories.CMFVRepository;
import com.Bootcamp.Project.Application.repositories.CategoryMetadataFieldRepository;
import com.Bootcamp.Project.Application.repositories.CategoryRepository;
import com.Bootcamp.Project.Application.services.serviceInterfaces.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CategoryImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    CategoryMetadataFieldRepository metadataFieldRepository;
    @Autowired
    CMFVRepository cmfvRepository;


    Pageable sortById = PageRequest.of(0, 10, Sort.by("id"));

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

        List<Category> categoryList = categories.get();

        List<CategoryResponseDTO> categoryResponseDTOList = new ArrayList<>();
        for (Category category : categoryList) {
            CategoryResponseDTO categoryResponseDTO = showCategory(category.getId());
            categoryResponseDTOList.add(categoryResponseDTO);
        }
        return categoryResponseDTOList;
    }


    @Override
    public CategoryResponseDTO showCategory(Long id) {
        ModelMapper modelMapper = new ModelMapper();
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new EcommerceException(ErrorCode.NOT_FOUND);
        }

        List<CategoryMetadataFieldValues> categoryMetadataFieldValuesList = cmfvRepository.fetchByCategoryId(category.get().getId());

        List<CMDResponseDTO> fieldList = new ArrayList<>();

        for (CategoryMetadataFieldValues c : categoryMetadataFieldValuesList) {

            CMDResponseDTO cmdResponseDTO = new CMDResponseDTO();

            if (c.getCategoryMetaField().getName() != null) {
                cmdResponseDTO.setName(c.getCategoryMetaField().getName());
                cmdResponseDTO.setValues(Arrays.asList(c.getFieldValues()));
                fieldList.add(cmdResponseDTO);
            }
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
        categoryResponseDTO.setFieldValues(fieldList);

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

  /*  private List<CategoryAddDTO> findSellerCategoryChildren(Long id) {
        List<Category> categoryList = categoryRepository.fetchLeafCategories();
        if (categoryList == null) {
            throw new EcommerceException(ErrorCode.NOT_FOUND);
        }

        for (Category category : categoryList) {
            List<CategoryMetadataFieldValues> categoryMetadataFieldValuesList = cmfvRepository.fetchByCategoryId(category.getId());

            List<CMDResponseDTO> fieldList = new ArrayList<>();

            for (CategoryMetadataFieldValues c : categoryMetadataFieldValuesList) {

                CMDResponseDTO cmdResponseDTO = new CMDResponseDTO();

                if (c.getCategoryMetaField().getName() != null) {
                    cmdResponseDTO.setName(c.getCategoryMetaField().getName());
                    cmdResponseDTO.setValues(Arrays.asList(c.getFieldValues()));
                    fieldList.add(cmdResponseDTO);
                }
            }

        }


        return null;
    }*/


    @Override
    public String addMetadata(String metaData) {
        ModelMapper modelMapper = new ModelMapper();
        Optional<CategoryMetadataField> metadataField = metadataFieldRepository.findByname(metaData);
        if (metadataField.isPresent()) {
            throw new EcommerceException(ErrorCode.ALREADY_EXISTS);
        }
        CategoryMetadataField categoryMetadataField = new CategoryMetadataField();
        categoryMetadataField.setName(metaData);
        metadataFieldRepository.save(categoryMetadataField);
        return "Metadata added successfully";
    }

    @Override
    public List<CategoryMetadataFieldDTO> showMetaData() {
        ModelMapper modelMapper = new ModelMapper();
        Optional<List<CategoryMetadataField>> metadataFieldList = metadataFieldRepository.fetchAll();
        if (metadataFieldList.isEmpty()) {
            throw new EcommerceException(ErrorCode.NO_DATA);
        }
        List<CategoryMetadataField> categoryMetadataFieldList = metadataFieldList.get();
        List<CategoryMetadataFieldDTO> categoryMetadataFieldDTOS = categoryMetadataFieldList
                .stream()
                .map(e -> modelMapper.map(e, CategoryMetadataFieldDTO.class))
                .collect(Collectors.toList());
        return categoryMetadataFieldDTOS;
    }

    @Override
    public String updateCategory(Long id, String updatedName) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            throw new EcommerceException(ErrorCode.NOT_FOUND);
        }
        Category categoryExists = categoryRepository.findByName(updatedName);
        if (categoryExists != null) {
            throw new EcommerceException(ErrorCode.NOT_FOUND);
        }
        category.setName(updatedName);
        categoryRepository.save(category);
        return "category updated successfully";
    }

    public String addMetadataValues(CategoryMetadataFieldValuesDTO cmdfvDTO) {
        ModelMapper modelMapper = new ModelMapper();

        Optional<Category> optionalCategory = categoryRepository.findById(cmdfvDTO.getCategoryId());
        if (optionalCategory.isEmpty()) {
            throw new EcommerceException(ErrorCode.NOT_FOUND);
        }
        Category category = optionalCategory.get();
        Optional<CategoryMetadataField> optionalMetadataField = metadataFieldRepository.findById(cmdfvDTO.getCategoryMetadataFieldId());
        if (optionalMetadataField.isEmpty()) {
            throw new EcommerceException(ErrorCode.NOT_FOUND);
        }
        CategoryMetadataField metadataField = optionalMetadataField.get();

        CategoryMetadataFieldValues categoryMetadataFieldValues = new CategoryMetadataFieldValues();

        String fieldValues = String.join(",", cmdfvDTO.getFieldValues());
        categoryMetadataFieldValues.setFieldValues(fieldValues);
        categoryMetadataFieldValues.setCategory(category);
        categoryMetadataFieldValues.setCategoryMetaField(metadataField);
        try {
            cmfvRepository.save(categoryMetadataFieldValues);
        } catch (RuntimeException e) {
            updateMetadataValues(cmdfvDTO);
        }
        return "metaData values saved successfully";//call update metadata value method here
    }


    @Override
    public String updateMetadataValues(CategoryMetadataFieldValuesDTO categoryMetadataFieldValuesDTO) {
        Optional<Category> category = categoryRepository.findById(categoryMetadataFieldValuesDTO.getCategoryId());
        if (category.isEmpty()) {
            throw new EcommerceException(ErrorCode.NOT_FOUND);
        }

        Optional<CategoryMetadataField> metadataField = metadataFieldRepository.findById(categoryMetadataFieldValuesDTO.getCategoryMetadataFieldId());
        if (metadataField.isEmpty()) {
            throw new EcommerceException(ErrorCode.NOT_FOUND);
        }

        CategoryMetadataFieldValues metadataFieldValues = cmfvRepository.fetchObject(category.get().getId(), metadataField.get().getId());

        if (metadataFieldValues == null) {
            throw new EcommerceException(ErrorCode.NOT_FOUND);
        }
        String oldValues = metadataFieldValues.getFieldValues();
        String newValues = String.join(",", categoryMetadataFieldValuesDTO.getFieldValues());
        String finalValues = checkFieldValues(oldValues, newValues);
        metadataFieldValues.setFieldValues(finalValues);
        cmfvRepository.save(metadataFieldValues);
        return "metadata values updated Successfully";
    }

    public String checkFieldValues(String oldValues, String newValues){
        String[] newValue = newValues.split(",");
        String updatedValues="";
        for (String str: newValue){
            if(!oldValues.contains(str)){
                updatedValues=oldValues+","+str;
            }
        }
        return  updatedValues;
    }

    @Override
    public List<SellerCategoryResponseDTO> showSellerCategories() {
        List<Category> categoryList = categoryRepository.fetchLeafCategories();
        if (categoryList == null) {
            throw new EcommerceException(ErrorCode.NOT_FOUND);
        }
        List<SellerCategoryResponseDTO> sellerCategoryResponseDTOList = new ArrayList<>();

        for (Category category : categoryList) {
            List<CategoryMetadataFieldValues> categoryMetadataFieldValuesList = cmfvRepository.fetchByCategoryId(category.getId());

            List<CMDResponseDTO> fieldList = new ArrayList<>();

            for (CategoryMetadataFieldValues c : categoryMetadataFieldValuesList) {

                CMDResponseDTO cmdResponseDTO = new CMDResponseDTO();

                if (c.getCategoryMetaField().getName() != null) {
                    cmdResponseDTO.setName(c.getCategoryMetaField().getName());
                    cmdResponseDTO.setValues(Arrays.asList(c.getFieldValues()));
                    fieldList.add(cmdResponseDTO);
                }
            }
            List<CategoryAddDTO> categoryAddDTOList = findCategoryParent(category.getId());
            SellerCategoryResponseDTO sellerCategoryResponseDTO = new SellerCategoryResponseDTO();
            sellerCategoryResponseDTO.setParentList(categoryAddDTOList);
            sellerCategoryResponseDTO.setName(category.getName());
            sellerCategoryResponseDTO.setFieldValues(fieldList);
            sellerCategoryResponseDTOList.add(sellerCategoryResponseDTO);
        }
        return sellerCategoryResponseDTOList;

    }

    @Override
    public List<CustomerCategoryResponseDTO> showCustomerCategories() {
        List<Category> categoryList = categoryRepository.fetchAllRootCategories(sortById);
        if (categoryList == null) {
            throw new EcommerceException(ErrorCode.NOT_FOUND);
        }

        List<CustomerCategoryResponseDTO> customerCategoryResponseDTOList = new ArrayList<>();
        for (Category category : categoryList) {
            CustomerCategoryResponseDTO customerCategoryResponseDTO = new CustomerCategoryResponseDTO();

            List<CategoryAddDTO> categoryChildren = findCategoryChildren(category.getId());
            customerCategoryResponseDTO.setName(category.getName());
            customerCategoryResponseDTO.setChildrenCategoryList(categoryChildren);
            customerCategoryResponseDTOList.add(customerCategoryResponseDTO);
        }
        return customerCategoryResponseDTOList;
    }

    @Override
    public CustomerCategoryResponseDTO showCustomerCategoriesParam(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new EcommerceException(ErrorCode.NOT_FOUND);
        }
        CustomerCategoryResponseDTO customerCategoryResponseDTO = new CustomerCategoryResponseDTO();
        List<CategoryAddDTO> categoryAddDTOList = findCategoryChildren(category.get().getId());
        customerCategoryResponseDTO.setName(category.get().getName());
        customerCategoryResponseDTO.setChildrenCategoryList(categoryAddDTOList);
        return customerCategoryResponseDTO;
    }

}
