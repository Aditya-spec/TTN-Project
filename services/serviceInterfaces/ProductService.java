package com.Bootcamp.Project.Application.services.serviceInterfaces;

import com.Bootcamp.Project.Application.dtos.ProductVariationDTO;
import com.Bootcamp.Project.Application.dtos.SellerProductAddDTO;
import com.Bootcamp.Project.Application.dtos.SellerProductShowDTO;
import com.Bootcamp.Project.Application.dtos.SellerProductUpdateDTO;

import java.util.List;

public interface ProductService {
    boolean addProduct(String email, SellerProductAddDTO sellerProductAddDTO);

    boolean activateProduct(Long id);

    boolean deActivateProduct(Long id);

    boolean deleteProduct(String email, Long id);

    boolean updateProduct(String email, SellerProductUpdateDTO sellerProductUpdateDTO, Long id);

    SellerProductShowDTO showSellerProduct(String email, Long id);

    List<SellerProductShowDTO> showAllSellerProducts(String email);

    boolean addVariation(String email, ProductVariationDTO productVariationDTO);
}
