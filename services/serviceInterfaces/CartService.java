package com.Bootcamp.Project.Application.services.serviceInterfaces;

public interface CartService {
    boolean addProduct(Long variationId, int quantity, String name);

    boolean updateCart(Long variationId, int quantity, String email);

    boolean deleteFromCart(Long variationId, String name);

    boolean emptyingCart(String name);
}
