package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.dtos.*;
import com.Bootcamp.Project.Application.entities.*;
import com.Bootcamp.Project.Application.enums.ErrorCode;
import com.Bootcamp.Project.Application.exceptionHandling.EcommerceException;
import com.Bootcamp.Project.Application.repositories.*;
import com.Bootcamp.Project.Application.services.serviceInterfaces.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ProductVariationRepository productVariationRepository;

    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;

    @Autowired
    CategoryMetadataFieldValuesRepository cmfvRepository;

    @Autowired
    CategoryImpl categoryImpl;

    ModelMapper modelMapper = new ModelMapper();

    int offset = 0;
    int size = 10;


    @Override
    public boolean addProduct(String email, SellerProductAddDTO sellerProductAddDTO) {

        Seller seller = sellerRepository.findByEmail(email);

        Long categoryId = sellerProductAddDTO.getCategoryId();                  //category is for category_id
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null) {
            throw new EcommerceException(ErrorCode.CATEGORY_NOT_EXIST);
        }

        List<Long> leafCategoriesId = categoryRepository.fetchLeafCategoryId();
        if (!(leafCategoriesId.contains(categoryId))) {
            throw new EcommerceException(ErrorCode.NOT_LEAF_CATEGORY);
        }
        checkProductUniqueness(sellerProductAddDTO.getName(), sellerProductAddDTO.getBrand(), categoryId, seller.getId());
        Product newProduct = mapProductFromDTO(sellerProductAddDTO);

        newProduct.setActive(false);
        category.setProduct(newProduct);
        seller.setProduct(newProduct);

        productRepository.save(newProduct);
        String body = "A new Product has been added by Seller: " + seller.getEmail() + "\nCategory: " + category.getName() + "\nBrand: "
                + newProduct.getBrand() + "please take some action.";
        String topic = "New Product Added!!";
        emailService.sendMail(seller.getEmail(), topic, body);

        return true;
    }


    @Override
    public boolean activateProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return false;
        }
        if (product.getActive()) {
            throw new EcommerceException(ErrorCode.ALREADY_ACTIVE);
        }
        product.setActive(true);
        productRepository.save(product);
        String body = "Your product = " + product.getName() + " has been  Activated.";
        emailService.sendMail(product.getSeller().getEmail(), "Product Activated", body);
        return true;
    }

    @Override
    public boolean deActivateProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return false;
        }
        if (!product.getActive()) {
            throw new EcommerceException(ErrorCode.ALREADY_DISACTIVE);
        }
        product.setActive(false);
        productRepository.save(product);
        String body = "Your product = " + product.getName() + " has been  de-activated.";
        emailService.sendMail(product.getSeller().getEmail(), "Product De-activated", body);
        return true;
    }

    @Override
    public boolean deleteProduct(String email, Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return false;
        }
        Seller seller = sellerRepository.findByEmail(email);
        if (seller.getId() != product.getSeller().getId()) {
            throw new EcommerceException(ErrorCode.NO_PRODUCT_FOUND);
        }
        product.setDeleted(true);
        productRepository.save(product);
        return true;
    }

    @Override
    public boolean updateProduct(String email, SellerProductUpdateDTO sellerProductUpdateDTO, Long id) {
        Seller seller = sellerRepository.findByEmail(email);
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new EcommerceException(ErrorCode.NO_PRODUCT_FOUND);
        }

        if (seller.getId() != product.getSeller().getId()) {
            throw new EcommerceException(ErrorCode.NOT_AUTHORISED);
        }

        if (sellerProductUpdateDTO != null) {
            checkProductUniqueness(sellerProductUpdateDTO.getName(), product.getBrand(), product.getCategory().getId(), seller.getId());
            product.setName(sellerProductUpdateDTO.getName());
        }
        if (sellerProductUpdateDTO.getCancellable()) {
            product.setCancellable(sellerProductUpdateDTO.getCancellable());
        }
        if (sellerProductUpdateDTO.getReturnable()) {
            product.setReturnable(sellerProductUpdateDTO.getReturnable());
        }
        if (sellerProductUpdateDTO.getDescription() != null) {
            product.setDescription(sellerProductUpdateDTO.getDescription());
        }
        productRepository.save(product);
        return true;
    }


    @Override
    public SellerProductShowDTO showSellerProduct(String email, Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new EcommerceException(ErrorCode.NO_PRODUCT_FOUND);
        }
        Seller seller = sellerRepository.findByEmail(email);
        if (seller.getId() != product.getSeller().getId()) {
            throw new EcommerceException(ErrorCode.NOT_AUTHORISED);
        }
        if (product.getDeleted()) {
            throw new EcommerceException(ErrorCode.NO_PRODUCT_FOUND);
        }
        SellerProductShowDTO sellerProductShowDTO = new SellerProductShowDTO();
        sellerProductShowDTO = showProductMappingToDTO(product, sellerProductShowDTO);
        return sellerProductShowDTO;
    }

    @Override
    public List<SellerProductShowDTO> showAllSellerProducts(String email, int offset, int size) {
        Seller seller = sellerRepository.findByEmail(email);
        if (size > 0) {
            this.offset = offset;
            this.size = size;
        }
        Pageable sortById = PageRequest.of(this.offset, this.size, Sort.by(Sort.Direction.ASC, "id"));
        List<Product> productList = productRepository.fetchBySellerId(seller.getId(), sortById);
        if (productList.size() == 0) {
            throw new EcommerceException(ErrorCode.NO_DATA);
        }
        List<SellerProductShowDTO> sellerProductShowDTOList = new ArrayList<>();
        for (Product product : productList) {
            if (!product.getDeleted()) {
                SellerProductShowDTO showDTO = new SellerProductShowDTO();
                showDTO = showProductMappingToDTO(product, showDTO);
                sellerProductShowDTOList.add(showDTO);
            }
        }
        return sellerProductShowDTOList;
    }


    public boolean addVariation(String email, ProductVariationDTO productVariationDTO) {
        Seller seller = sellerRepository.findByEmail(email);

        Product product = productRepository.findById(productVariationDTO.getProductId()).orElse(null);
        if (product == null) {
            throw new EcommerceException(ErrorCode.NO_PRODUCT_FOUND);
        }
        if (seller.getId() != product.getSeller().getId()) {
            throw new EcommerceException(ErrorCode.NOT_AUTHORISED);
        }
        if (!product.getActive()) {
            throw new EcommerceException(ErrorCode.NOT_ACTIVE);
        }
        if (product.getDeleted()) {
            throw new EcommerceException(ErrorCode.NOT_FOUND);
        }
        Category category = categoryRepository.findById(product.getCategory().getId()).orElse(null);  //to get meta values
        JSONArray inputMetadata = productVariationDTO.getMetadata();
        checkVariation(inputMetadata, product);             //check whether the given field and values is present or not

        ProductVariation productVariation = new ProductVariation();
        productVariation = addVariationMappingFromDTO(productVariation, productVariationDTO, product);
        productVariationRepository.save(productVariation);

        return true;

    }


    @Override
    public boolean updateVariation(String email, ProductVariationUpdateDTO productVariationUpdateDTO, Long variationId) {
        Seller seller = sellerRepository.findByEmail(email);
        ProductVariation productVariation = productVariationRepository.findById(variationId).orElse(null);
        if (productVariation == null) {
            throw new EcommerceException(ErrorCode.NOT_FOUND);
        }

        Product product = productRepository.findById(productVariation.getProduct().getId()).orElse(null);

        if (seller.getId() != product.getSeller().getId()) {
            throw new EcommerceException(ErrorCode.NOT_AUTHORISED);
        }
        if (!product.getActive()) {
            throw new EcommerceException(ErrorCode.NOT_ACTIVE);
        }

        if (product.getDeleted()) {
            throw new EcommerceException(ErrorCode.NOT_FOUND);
        }

        if (productVariationUpdateDTO.getMetadata() != null) {
            JSONArray inputMetadata = productVariationUpdateDTO.getMetadata();
            checkVariation(inputMetadata, product);
        }

        productVariation = updateVariationMapping(productVariation, productVariationUpdateDTO);
        productVariationRepository.save(productVariation);
        return true;
    }

    @Override
    public ProductVariationResponseDTO showVariation(String email, Long variationId) {

        ProductVariation productVariation = productVariationRepository.findById(variationId).orElse(null);
        if (productVariation == null) {
            throw new EcommerceException(ErrorCode.NO_DATA);
        }
        Product product = productVariation.getProduct();
        if (product.getDeleted()) {
            throw new EcommerceException(ErrorCode.NO_PRODUCT_FOUND);
        }
        Seller seller = product.getSeller();

        if (seller.getId() != product.getSeller().getId()) {
            throw new EcommerceException(ErrorCode.NOT_AUTHORISED);
        }
        ProductVariationResponseDTO responseDTO = showVariationMapping(product, productVariation);
        return responseDTO;
    }

    @Override
    public List<ProductVariationResponseDTO> showProductVariations(String email, Long productId, int offset, int size) {
        Seller seller = sellerRepository.findByEmail(email);
        Product product = productRepository.findById(productId).orElse(null);
        if (seller.getId() != product.getSeller().getId()) {
            throw new EcommerceException(ErrorCode.NOT_AUTHORISED);
        }
        if (product.getDeleted()) {
            throw new EcommerceException(ErrorCode.NO_PRODUCT_FOUND);
        }
        if (size > 0) {
            this.offset = offset;
            this.size = size;
        }
        Pageable sortById = PageRequest.of(this.offset, this.size, Sort.by(Sort.Direction.ASC, "id"));
        List<ProductVariation> variationList = productVariationRepository.fetchVariations(product.getId(), sortById);
        if (variationList.size() == 0) {
            throw new EcommerceException(ErrorCode.NO_DATA);
        }
        List<ProductVariationResponseDTO> productVariationList = new ArrayList<>();
        for (ProductVariation variation : variationList) {
            ProductVariationResponseDTO responseDTO = showVariationMapping(product, variation);
            productVariationList.add(responseDTO);
        }
        return productVariationList;
    }


    public AdminCustomerProductResponseDTO showCustomerProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if ((product == null)) {
            throw new EcommerceException(ErrorCode.NO_PRODUCT_FOUND);
        }
        if (product.getDeleted()) {
            throw new EcommerceException(ErrorCode.NO_PRODUCT_FOUND);
        }
        if (!product.getActive()) {
            throw new EcommerceException(ErrorCode.NOT_ACTIVE);
        }
        AdminCustomerProductResponseDTO responseDTO = fetchProductWithVariations(product);
        if (responseDTO.getVariationsList().isEmpty()) {
            throw new EcommerceException(ErrorCode.NO_PRODUCT_FOUND);
        }
        return responseDTO;
    }

    @Override
    public List<AdminCustomerProductResponseDTO> showAdminAllProducts(int offset, int size) {
        if (size > 0) {
            this.offset = offset;
            this.size = size;
        }
        Pageable sortById = PageRequest.of(this.offset, this.size, Sort.by(Sort.Direction.ASC, "id"));
        List<Product> productList = productRepository.fetchAllProducts(sortById);
        List<AdminCustomerProductResponseDTO> adminCustomerProductResponseDTOList = new ArrayList<>();
        if (productList.size() == 0) {
            throw new EcommerceException(ErrorCode.NO_DATA);
        }

        for (Product product : productList) {
            adminCustomerProductResponseDTOList.add(fetchProductWithVariations(product));
        }
        return adminCustomerProductResponseDTOList;
    }

    @Override
    public AdminCustomerProductResponseDTO showAdminProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new EcommerceException(ErrorCode.NO_PRODUCT_FOUND);
        }
        return fetchProductWithVariations(product);
    }

    @Override
    public List<AdminCustomerProductResponseDTO> viewSimilarProduct(Long productId, int offset, int size) {
        Product givenProduct = productRepository.findById(productId).orElse(null);
        if (givenProduct == null) {
            throw new EcommerceException(ErrorCode.NO_PRODUCT_FOUND);
        }

        Pageable sortById = PageRequest.of(this.offset, this.size, Sort.by(Sort.Direction.ASC, "id"));
        List<Product> productList = productRepository.fetchSimilarProducts(givenProduct.getCategory().getId(), sortById);
        if (productList.size() == 0) {
            throw new EcommerceException(ErrorCode.NO_DATA);
        }
        List<AdminCustomerProductResponseDTO> adminCustomerProductResponseDTOList = new ArrayList<>();
        for (Product product : productList) {
            if (!product.getDeleted() && product.getActive()) {
             AdminCustomerProductResponseDTO adminCustomerProductResponseDTO=fetchProductWithVariations(product);
             if(!adminCustomerProductResponseDTO.getVariationsList().isEmpty())
             adminCustomerProductResponseDTOList.add(adminCustomerProductResponseDTO);
            }
        }
        return adminCustomerProductResponseDTOList;
    }


    @Override
    public List<AdminCustomerProductResponseDTO> showCustomerProducts(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null) {
            throw new EcommerceException(ErrorCode.CATEGORY_NOT_EXIST);
        }
        List<Product> productList = new ArrayList<>();

        List<Category> allLeafCategory = categoryRepository.fetchLeafCategories();
        if (allLeafCategory.size() == 0) {
            throw new EcommerceException(ErrorCode.NO_PRODUCT_FOUND);
        }
        productList = getAllProducts(category, productList, allLeafCategory);
        if (productList == null) {
            throw new EcommerceException(ErrorCode.NO_PRODUCT_FOUND);
        }
        List<AdminCustomerProductResponseDTO> responseDTOList = new ArrayList<>();
        for (Product product : productList) {
            if (!product.getDeleted() && product.getActive()) {
                AdminCustomerProductResponseDTO adminCustomerProductResponseDTO = fetchProductWithVariations(product);
                if (!adminCustomerProductResponseDTO.getVariationsList().isEmpty())
                    responseDTOList.add(adminCustomerProductResponseDTO);
            }
        }
        return responseDTOList;
    }


    /**
     * Utility Functions
     */

    private Product mapProductFromDTO(SellerProductAddDTO sellerProductAddDTO) {
        Product product = new Product();
        product.setCancellable(sellerProductAddDTO.getCancellable());
        product.setName(sellerProductAddDTO.getName());
        product.setDescription(sellerProductAddDTO.getDescription());
        product.setBrand(sellerProductAddDTO.getBrand());
        product.setReturnable(sellerProductAddDTO.getReturnable());
        return product;
    }

    private void checkProductUniqueness(String name, String brand, Long categoryId, Long sellerId) {
        List<Product> productList = productRepository.fetchProduct(brand, categoryId, sellerId);
        if (productList != null) {
            for (Product product : productList) {
                if (name.equalsIgnoreCase(product.getName())) {
                    throw new EcommerceException(ErrorCode.NOT_UNIQUE);
                }
            }
        }
    }

    /**
     * Fetches all the variations of a product and maps them into DTO
     */

    private AdminCustomerProductResponseDTO fetchProductWithVariations(Product product) {
        Category category = categoryRepository.findById(product.getCategory().getId()).orElse(null);

        Pageable sortById = PageRequest.of(this.offset, this.size, Sort.by(Sort.Direction.ASC, "id"));
        List<ProductVariation> variationList = productVariationRepository.fetchVariations(product.getId(), sortById);

        List<ProductVariationResponseDTO> productVariationList = new ArrayList<>();
        if (variationList.size() != 0) {
            for (ProductVariation variation : variationList) {
                ProductVariationResponseDTO responseDTO = showVariationMapping(product, variation);
                productVariationList.add(responseDTO);
            }
        }
        AdminCustomerProductResponseDTO productResponseDTO = new AdminCustomerProductResponseDTO();
        productResponseDTO = mappingCustomerProduct(product, category);
        productResponseDTO.setVariationsList(productVariationList);
        return productResponseDTO;
    }


    private AdminCustomerProductResponseDTO mappingCustomerProduct(Product product, Category category) {
        AdminCustomerProductResponseDTO responseDTO = new AdminCustomerProductResponseDTO();
        responseDTO.setProductName(product.getName());
        responseDTO.setActive(product.getActive());
        responseDTO.setBrand(product.getBrand());
        responseDTO.setDescription(product.getDescription());
        responseDTO.setCancellable(product.getCancellable());
        responseDTO.setReturnable(product.getReturnable());
        responseDTO.setSellerName(product.getSeller().getName().getFirstName());
        responseDTO.setSellerContact(product.getSeller().getCompanyContact());
        responseDTO.setProductId(product.getId());
        responseDTO.setCategoryId(category.getId());
        responseDTO.setCategoryName(category.getName());
        responseDTO.setParentCategoryId(category.getParentId());

        return responseDTO;
    }


    private ProductVariationResponseDTO showVariationMapping(Product product, ProductVariation productVariation) {

        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        ProductVariationResponseDTO responseDTO = new ProductVariationResponseDTO();
        responseDTO.setProductDTO(productDTO);
        responseDTO.setMetadata((List<Object>) productVariation.getMetadata().get("metadata"));
        responseDTO.setPrice(productVariation.getPrice());
        responseDTO.setPrimaryImageName(productVariation.getPrimaryImageName());
        responseDTO.setQuantityAvailable(productVariation.getQuantityAvailable());
        responseDTO.setActive(productVariation.getActive());
        responseDTO.setVariationId(productVariation.getId());
        responseDTO.setProductId(product.getId());
        return responseDTO;
    }

    private ProductVariation updateVariationMapping(ProductVariation productVariation, ProductVariationUpdateDTO productVariationUpdateDTO) {
        if (productVariationUpdateDTO.getMetadata() != null) {
            JSONObject metadata = new JSONObject();
            metadata.put("metadata", productVariationUpdateDTO.getMetadata());
            productVariation.setMetadata(metadata);
        }
        if (productVariationUpdateDTO.getPrice() != null) {
            productVariation.setPrice(productVariationUpdateDTO.getPrice());
        }
        if (productVariationUpdateDTO.getActive() != null) {
            productVariation.setActive(productVariationUpdateDTO.getActive());
        }
        if (productVariationUpdateDTO.getPrimaryImageName() != null) {
            productVariation.setPrimaryImageName(productVariationUpdateDTO.getPrimaryImageName());
        }
        if (productVariationUpdateDTO.getQuantityAvailable() != productVariation.getQuantityAvailable()) {
            productVariation.setQuantityAvailable(productVariationUpdateDTO.getQuantityAvailable());
        }

        return productVariation;
    }


    private ProductVariation addVariationMappingFromDTO(ProductVariation productVariation, ProductVariationDTO productVariationDTO, Product product) {
        JSONObject metadata = new JSONObject();
        metadata.put("metadata", productVariationDTO.getMetadata());
        productVariation.setMetadata(metadata);

        productVariation.setPrice(productVariationDTO.getPrice());
        productVariation.setPrimaryImageName(productVariationDTO.getPrimaryImageName());
        productVariation.setQuantityAvailable(productVariationDTO.getQuantityAvailable());
        productVariation.setProduct(product);
        product.setActive(true);
        return productVariation;
    }


    private SellerProductShowDTO showProductMappingToDTO(Product product, SellerProductShowDTO showDTO) {
        showDTO.setActive(product.getActive());
        showDTO.setBrand(product.getBrand());
        showDTO.setId(product.getId());
        showDTO.setCategoryId(product.getCategory().getId());
        showDTO.setDescription(product.getDescription());
        showDTO.setCategoryName(product.getCategory().getName());
        showDTO.setReturnable(product.getReturnable());
        showDTO.setCancellable(product.getCancellable());
        showDTO.setName(product.getName());
        return showDTO;
    }

    /**
     * Checks whether the given meta field and meta values exist in the database or not
     */

    private void checkVariation(JSONArray inputMetadata, Product product) {

        for (Object object : inputMetadata) {
            Map<String, Object> metaValues = objectMapper.convertValue(object, Map.class);
            String fieldName = metaValues.get("field").toString();
            String value = metaValues.get("values").toString();

            CategoryMetadataField categoryMetadataField = categoryMetadataFieldRepository.findByname(fieldName).orElse(null);
            if (categoryMetadataField == null) {
                throw new EcommerceException(ErrorCode.NO_METADATAFIELD_EXIST);
            }
            String dbMetaValues = cmfvRepository.fetchMetaDataValues(product.getCategory().getId(), categoryMetadataField.getId());
            if (dbMetaValues == null) {
                throw new EcommerceException(ErrorCode.NOT_FOUND);
            }
            List<String> dbMetaValuesList = Arrays.asList(dbMetaValues.split(","));

            if (!dbMetaValuesList.contains(value)) {
                throw new EcommerceException(ErrorCode.NO_METAVALUES_EXIST);
            }
        }

    }

    /**
     *
     */

    private List<Product> getAllProducts(Category category, List<Product> productList, List<Category> allLeafCategory) {

        if (allLeafCategory.contains(category)) {
            List<Product> categoryProducts = productRepository.fetchAllCategoryProducts(category.getId());
            if ((categoryProducts.size() != 0)) {
                productList.addAll(categoryProducts);
            }
            return productList;
        }

        Optional<List<Category>> childCategories = categoryRepository.findNextChildren(category.getId());
        List<Category> categoryList = childCategories.get();
        for (Category child : categoryList) {
            productList = getAllProducts(child, productList, allLeafCategory);
        }
        return productList;
    }


}
