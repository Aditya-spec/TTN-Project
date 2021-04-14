package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.entities.Cart;
import com.Bootcamp.Project.Application.entities.Customer;
import com.Bootcamp.Project.Application.entities.Product;
import com.Bootcamp.Project.Application.entities.ProductVariation;
import com.Bootcamp.Project.Application.enums.ErrorCode;
import com.Bootcamp.Project.Application.exceptionHandling.EcommerceException;
import com.Bootcamp.Project.Application.repositories.CartRepository;
import com.Bootcamp.Project.Application.repositories.CustomerRepository;
import com.Bootcamp.Project.Application.repositories.ProductRepository;
import com.Bootcamp.Project.Application.repositories.ProductVariationRepository;
import com.Bootcamp.Project.Application.services.serviceInterfaces.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class CartImpl implements CartService {
    @Autowired
    ProductVariationRepository variationRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    ProductRepository productRepository;

    Logger logger= LoggerFactory.getLogger(getClass().getName());


    @Override
    public boolean addProduct(Long variationId, int quantity, String email) {
        if (quantity <= 0) {
            throw new EcommerceException(ErrorCode.INVALID_QUANTITY);
        }
        ProductVariation productVariation = variationRepository.findById(variationId).orElse(null);
        Product product = productRepository.findById(productVariation.getProduct().getId()).orElse(null);
        if (productVariation == null || product.getDeleted()) {
            throw new EcommerceException(ErrorCode.NO_VARIATION_FOUND);
        }
        if (!productVariation.getActive()) {
            throw new EcommerceException(ErrorCode.NOT_ACTIVE);
        }
        Customer customer = customerRepository.findByEmail(email);
        Cart existedCart = cartRepository.fetchCartByCustomerAndVariation(customer.getId(), variationId);
        if (existedCart != null) {
            existedCart.setQuantity(quantity);
            cartRepository.save(existedCart);
            return true;
        }
        Cart cart = new Cart();
        customer.setCart(cart);
        productVariation.setCart(cart);
        cart.setQuantity(quantity);
        cartRepository.save(cart);
        return true;
    }

    @Override
    public boolean updateCart(Long variationId, int quantity, String email) {
        if (quantity <= 0) {
            throw new EcommerceException(ErrorCode.INVALID_QUANTITY);
        }
        ProductVariation productVariation = variationRepository.findById(variationId).orElse(null);
        Product product = productRepository.findById(productVariation.getProduct().getId()).orElse(null);
        if (productVariation == null || product.getDeleted()) {
            throw new EcommerceException(ErrorCode.NO_VARIATION_FOUND);
        }
        if (!productVariation.getActive()) {
            throw new EcommerceException(ErrorCode.NOT_ACTIVE);
        }
        Customer customer = customerRepository.findByEmail(email);
        Cart cart = cartRepository.fetchCartByCustomerAndVariation(customer.getId(), variationId);
        if (cart == null) {
            throw new EcommerceException(ErrorCode.CART_NOT_FOUND);
        }
        cart.setQuantity(quantity);
        cartRepository.save(cart);
        return true;
    }

    @Override
    public boolean deleteFromCart(Long variationId, String email) {
        ProductVariation productVariation = variationRepository.findById(variationId).orElse(null);
        if (productVariation == null) {
            throw new EcommerceException(ErrorCode.NO_VARIATION_FOUND);
        }
        Customer customer = customerRepository.findByEmail(email);
        Cart cart = cartRepository.fetchCartByCustomerAndVariation(customer.getId(), variationId);
        if (cart == null) {
            throw new EcommerceException(ErrorCode.CART_NOT_FOUND);
        }
        cartRepository.delete(cart);
        return true;

    }

    @Override
    public boolean emptyingCart(String email) {
        Customer customer = customerRepository.findByEmail(email);
        List<Cart> cartList=cartRepository.fetchByCustomer(customer.getId());
        if(cartList==null){
            throw new EcommerceException(ErrorCode.CART_NOT_FOUND);
        }
        cartList.stream().forEach(e->cartRepository.delete(e));
        return true;

    }
}
