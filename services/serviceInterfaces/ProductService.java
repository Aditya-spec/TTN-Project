package com.Bootcamp.Project.Application.services.serviceInterfaces;

import com.Bootcamp.Project.Application.dtos.*;

import java.util.List;

public interface ProductService {
    boolean addProduct(String email, SellerProductAddDTO sellerProductAddDTO);

    boolean activateProduct(Long id);

    boolean deActivateProduct(Long id);

    boolean deleteProduct(String email, Long id);

    boolean updateProduct(String email, SellerProductUpdateDTO sellerProductUpdateDTO, Long id);

    SellerProductShowDTO showSellerProduct(String email, Long id);

    List<SellerProductShowDTO> showAllSellerProducts(String email, int offset, int size);

    boolean addVariation(String email, ProductVariationDTO productVariationDTO);


    boolean updateVariation(String email, ProductVariationUpdateDTO productVariationUpdateDTO, Long id);

    ProductVariationResponseDTO showVariation(String email, Long id);

    List<ProductVariationResponseDTO> showProductVariations(String email, Long productId, int offset, int size);

    AdminCustomerProductResponseDTO showCustomerProduct(Long id);

    List<AdminCustomerProductResponseDTO> showAdminAllProducts(int offset, int size);

    AdminCustomerProductResponseDTO showAdminProduct(Long id);

    List<AdminCustomerProductResponseDTO> viewSimilarProduct(Long productId, int offset, int size);

    List<AdminCustomerProductResponseDTO> showCustomerProducts(Long categoryId);
}
