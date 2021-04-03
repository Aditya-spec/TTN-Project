package com.Bootcamp.Project.Application.services.serviceInterfaces;

import com.Bootcamp.Project.Application.dtos.SellerProductAddDTO;

public interface ProductService {
    boolean addProduct(String email, SellerProductAddDTO sellerProductAddDTO);

    boolean activateProduct(Long id);

    boolean deActivateProduct(Long id);

    boolean deleteProduct(String email, Long id);
}
