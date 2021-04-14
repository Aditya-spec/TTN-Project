package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.dtos.CartResponseDTO;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


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

    Logger logger = LoggerFactory.getLogger(getClass().getName());


    @Override
    public boolean addProduct(Long variationId, int quantity, String email) {
        Cart existedCart = getExistingCart(variationId, quantity, email);
        if (existedCart != null) {
            existedCart.setQuantity(quantity);
            cartRepository.save(existedCart);
            return true;
        }
        ProductVariation productVariation = variationRepository.findById(variationId).orElse(null);
        Customer customer = customerRepository.findByEmail(email);

        Cart cart = new Cart();
        customer.setCart(cart);
        productVariation.setCart(cart);
        cart.setQuantity(quantity);
        cartRepository.save(cart);
        return true;
    }

    @Override
    public boolean updateCart(Long variationId, int quantity, String email) {
        if (quantity == 0) {
            deleteCart(variationId, email);
            return false;
        }
        Cart cart = getExistingCart(variationId, quantity, email);
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
    public boolean cleanCart(String email) {
        Customer customer = customerRepository.findByEmail(email);
        List<Cart> cartList = cartRepository.fetchByCustomer(customer.getId());
        if (cartList.size() == 0) {
            throw new EcommerceException(ErrorCode.CART_NOT_FOUND);
        }
        cartList.stream().forEach(e -> cartRepository.delete(e));
        return true;
    }

    @Override
    public List<CartResponseDTO> viewCart(String email) {
        Customer customer = customerRepository.findByEmail(email);
        List<Cart> cartList = cartRepository.fetchByCustomer(customer.getId());
        if (cartList.size() == 0) {
            throw new EcommerceException(ErrorCode.NO_DATA);
        }
        return cartList.stream().map(e -> showCartMapping(e)).filter(e -> e != null).collect(Collectors.toList());
    }

    /**
     * Utility functions
     *
     */

    private Cart getExistingCart(Long variationId, int quantity, String email) {
        if (quantity <= 0) {
            throw new EcommerceException(ErrorCode.INVALID_QUANTITY);
        }
        ProductVariation productVariation = variationRepository.findById(variationId).orElse(null);
        if (productVariation == null) {
            throw new EcommerceException(ErrorCode.NO_VARIATION_FOUND);
        }
        if (!productVariation.getActive()) {
            throw new EcommerceException(ErrorCode.NOT_ACTIVE);
        }
        Product product = productRepository.findById(productVariation.getProduct().getId()).orElse(null);
        if (product.getDeleted() || !product.getActive()) {
            throw new EcommerceException(ErrorCode.NO_PRODUCT_FOUND);
        }
        Customer customer = customerRepository.findByEmail(email);
        Cart cart = cartRepository.fetchCartByCustomerAndVariation(customer.getId(), variationId);
        return cart;
    }

    private void deleteCart(Long variationId, String email) {
        Customer customer = customerRepository.findByEmail(email);
        ProductVariation productVariation = variationRepository.findById(variationId).orElse(null);
        if (productVariation == null) {
            throw new EcommerceException(ErrorCode.NO_VARIATION_FOUND);
        }
        Cart cart = cartRepository.fetchCartByCustomerAndVariation(customer.getId(), variationId);
        if (cart == null) {
            throw new EcommerceException(ErrorCode.CART_NOT_FOUND);
        }
        cartRepository.delete(cart);
    }

    private CartResponseDTO showCartMapping(Cart cart) {
        CartResponseDTO cartResponseDTO = new CartResponseDTO();
        ProductVariation productVariation = variationRepository.findById(cart.getProductVariation().getId()).get();
        Product product = productRepository.findById(productVariation.getProduct().getId()).get();
        if (product.getDeleted() || !product.getActive()) {
            return null;
        }
        cartResponseDTO.setVariationId(productVariation.getId());
        cartResponseDTO.setActive(productVariation.getActive());
        cartResponseDTO.setPrice(productVariation.getPrice());
        cartResponseDTO.setPrimaryImageName(productVariation.getPrimaryImageName());
        cartResponseDTO.setQuantityAvailable(cart.getQuantity());
        cartResponseDTO.setBrandName(product.getBrand());
        cartResponseDTO.setOutOfStock(productVariation.getQuantityAvailable() <= 0);
        return cartResponseDTO;


    }
}
