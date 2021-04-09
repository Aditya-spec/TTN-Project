package com.Bootcamp.Project.Application.services;


import com.Bootcamp.Project.Application.dtos.*;
import com.Bootcamp.Project.Application.entities.Category;
import com.Bootcamp.Project.Application.entities.CategoryMetadataField;
import com.Bootcamp.Project.Application.entities.categoryMetadataFieldValues.CategoryMetadataFieldValues;
import com.Bootcamp.Project.Application.enums.ErrorCode;
import com.Bootcamp.Project.Application.exceptionHandling.EcommerceException;
import com.Bootcamp.Project.Application.repositories.*;
import com.Bootcamp.Project.Application.services.serviceInterfaces.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class CategoryImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    CategoryMetadataFieldRepository metadataFieldRepository;
    @Autowired
    CMFVRepository cmfvRepository;
    @Autowired
    ProductVariationRepository productVariationRepository;
    @Autowired
    ProductRepository productRepository;

    ModelMapper modelMapper = new ModelMapper();
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

        Optional<List<Category>> categories = categoryRepository.fetchALlCategories(sortById);
        if (categories.isEmpty()) {
            throw new EcommerceException(ErrorCode.CATEGORY_NOT_EXIST);
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

        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new EcommerceException(ErrorCode.CATEGORY_NOT_EXIST);
        }

        List<CategoryMetadataFieldValues> categoryMetadataFieldValuesList = cmfvRepository.fetchByCategoryId(category.get().getId());
        if (categoryMetadataFieldValuesList.size() == 0) {
            throw new EcommerceException(ErrorCode.NO_DATA);
        }

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


    @Override
    public String addMetadata(String metaData) {

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
    public List<CMDResponseDTO> showMetaData() {

        Optional<List<CategoryMetadataField>> metadataFieldList = metadataFieldRepository.fetchAll(sortById);
        if (metadataFieldList.isEmpty()) {
            throw new EcommerceException(ErrorCode.NO_DATA);
        }
        List<CategoryMetadataField> categoryMetadataFieldList = metadataFieldList.get();

        List<CMDResponseDTO> cmdResponseDTOS = new ArrayList<>();

        for (CategoryMetadataField metadataField : categoryMetadataFieldList) {

            List<CategoryMetadataFieldValues> fieldValuesList = cmfvRepository.fetchByMetaId(metadataField.getId());

            CMDResponseDTO metadataFieldDTO = new CMDResponseDTO();
            if (fieldValuesList.size() == 0) {
                throw new EcommerceException(ErrorCode.NO_DATA);
            }

            List<String> collect = fieldValuesList.stream().map(e -> e.getFieldValues()).collect(Collectors.toList());

            List<String> metaValues = collect.stream().distinct().collect(Collectors.toList());

            metadataFieldDTO.setValues(metaValues);
            metadataFieldDTO.setName(metadataField.getName());

            cmdResponseDTOS.add(metadataFieldDTO);

        }
        return cmdResponseDTOS;
    }

    @Override
    public String updateCategory(Long categoryId, String updatedName) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null) {
            throw new EcommerceException(ErrorCode.CATEGORY_NOT_EXIST);
        }
        Category categoryExists = categoryRepository.findByName(updatedName);
        if (categoryExists != null) {
            throw new EcommerceException(ErrorCode.ALREADY_EXISTS);
        }
        category.setName(updatedName);
        categoryRepository.save(category);
        return "category updated successfully";
    }


    @Override
    public String addMetadataValues(CategoryMetadataFieldValuesDTO cmdfvDTO) {

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
        return "metaData values saved successfully";
    }


    @Override
    public String updateMetadataValues(CategoryMetadataFieldValuesDTO categoryMetadataFieldValuesDTO) {
        Optional<Category> category = categoryRepository.findById(categoryMetadataFieldValuesDTO.getCategoryId());
        if (category.isEmpty()) {
            throw new EcommerceException(ErrorCode.CATEGORY_NOT_EXIST);
        }

        Optional<CategoryMetadataField> metadataField = metadataFieldRepository.findById(categoryMetadataFieldValuesDTO.getCategoryMetadataFieldId());
        if (metadataField.isEmpty()) {
            throw new EcommerceException(ErrorCode.NO_DATA);
        }

        CategoryMetadataFieldValues metadataFieldValues = cmfvRepository.fetchObject(category.get().getId(), metadataField.get().getId());

        if (metadataFieldValues == null) {
            throw new EcommerceException(ErrorCode.NO_DATA);
        }
        String oldValues = metadataFieldValues.getFieldValues();
        String newValues = String.join(",", categoryMetadataFieldValuesDTO.getFieldValues());
        String finalValues = checkFieldValues(oldValues, newValues);
        metadataFieldValues.setFieldValues(finalValues);
        cmfvRepository.save(metadataFieldValues);
        return "metadata values updated Successfully";
    }


    @Override
    public List<SellerCategoryResponseDTO> showSellerCategories() {
        List<Category> categoryList = categoryRepository.fetchLeafCategories();
        if (categoryList.size() == 0) {
            throw new EcommerceException(ErrorCode.NO_DATA);
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
        if (categoryList.size() == 0) {
            throw new EcommerceException(ErrorCode.NO_DATA);
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
            throw new EcommerceException(ErrorCode.CATEGORY_NOT_EXIST);
        }
        CustomerCategoryResponseDTO customerCategoryResponseDTO = new CustomerCategoryResponseDTO();
        List<CategoryAddDTO> categoryAddDTOList = findCategoryChildren(category.get().getId());
        customerCategoryResponseDTO.setName(category.get().getName());
        customerCategoryResponseDTO.setChildrenCategoryList(categoryAddDTOList);
        return customerCategoryResponseDTO;
    }

    @Override
    public CustomerCategoryFilterDTO filter(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null) {
            throw new EcommerceException(ErrorCode.CATEGORY_NOT_EXIST);
        }
        Double maxPrice = productVariationRepository.getMaxPrice(categoryId);
        Double minPrice = productVariationRepository.getMinPrice(categoryId);

        List<String> brandList = productRepository.fetchBrandList(categoryId);
        if (brandList.size() == 0) {
            throw new EcommerceException(ErrorCode.NO_DATA);
        }

        CustomerCategoryFilterDTO categoryFilterDTO = new CustomerCategoryFilterDTO();

        List<CategoryMetadataFieldValues> fieldValuesList = cmfvRepository.fetchByCategoryId(categoryId);
        List<CMDResponseDTO> cmdResponseDTOList = new ArrayList<>();
        for (CategoryMetadataFieldValues fieldValues : fieldValuesList) {
            CMDResponseDTO responseDTO = new CMDResponseDTO();
            responseDTO.setName(fieldValues.getCategoryMetaField().getName());
            responseDTO.setValues(Arrays.asList(fieldValues.getFieldValues()));
            cmdResponseDTOList.add(responseDTO);
        }
        categoryFilterDTO.setFieldValuesList(cmdResponseDTOList);
        categoryFilterDTO.setCategoryId(categoryId);
        categoryFilterDTO.setCategoryName(category.getName());
        categoryFilterDTO.setBrandNames(brandList);
        categoryFilterDTO.setMaxPrice(maxPrice);
        categoryFilterDTO.setMinPrice(minPrice);
        return categoryFilterDTO;
    }


    /**
     * Utility Functions
     */

    private List<CategoryAddDTO> findCategoryParent(Long id) {

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

        Optional<List<Category>> categories = categoryRepository.findNextChildren(id);
        if (categories.isEmpty()) {
            return null;
        }
        List<Category> categoryList = categories.get();
        List<CategoryAddDTO> categoryAddDTOList = categoryList.stream().map(e -> modelMapper.map(e, CategoryAddDTO.class)).collect(Collectors.toList());
        return categoryAddDTOList;
    }

    public String checkFieldValues(String oldValues, String newValues) {
        String[] newValue = newValues.split(",");
        String updatedValues = oldValues;
        for (String str : newValue) {
            if (!oldValues.contains(str)) {
                updatedValues = updatedValues + "," + str;
            }
        }
        return updatedValues;
    }

}
