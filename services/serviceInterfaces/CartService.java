package com.Bootcamp.Project.Application.services.serviceInterfaces;

import com.Bootcamp.Project.Application.dtos.CartResponseDTO;

import java.util.List;

public interface CartService {
    boolean addProduct(Long variationId, int quantity, String name);

    boolean updateCart(Long variationId, int quantity, String email);

    boolean deleteFromCart(Long variationId, String name);

    boolean cleanCart(String name);

    List<CartResponseDTO> viewCart(String email);
}
