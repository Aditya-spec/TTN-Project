package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.dtos.DirectOrderDTO;
import com.Bootcamp.Project.Application.dtos.MessageDTO;
import com.Bootcamp.Project.Application.dtos.PartialProductsOrderDTO;
import com.Bootcamp.Project.Application.entities.*;
import com.Bootcamp.Project.Application.enums.ErrorCode;
import com.Bootcamp.Project.Application.enums.FromStatus;
import com.Bootcamp.Project.Application.enums.PaymentMethod;
import com.Bootcamp.Project.Application.enums.ToStatus;
import com.Bootcamp.Project.Application.exceptionHandling.EcommerceException;
import com.Bootcamp.Project.Application.repositories.*;
import com.Bootcamp.Project.Application.services.serviceInterfaces.InvoiceService;
import com.Bootcamp.Project.Application.validations.CustomValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceImpl implements InvoiceService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    ProductVariationRepository variationRepository;
    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    OrderProductRepository orderProductRepository;
    @Autowired
    OrderStatusRepository orderStatusRepository;
    @Autowired
    MessageDTO messageDTO;
    @Autowired
    CustomValidation customValidation;

    @Override
    public ResponseEntity<MessageDTO> orderAllProducts(Long addressID, String paymentMethod, String email) {
        Customer customer = customerRepository.findByEmail(email);
        Address address = addressRepository.findById(addressID).orElse(null);
        ResponseEntity<MessageDTO> addressValidationResponse = validateAddress(customer, address);
        if (addressValidationResponse != null) {
            return addressValidationResponse;
        }
        List<Cart> cartList = cartRepository.fetchByCustomer(customer.getId());
        if (cartList.size() == 0) {
            throw new EcommerceException(ErrorCode.CART_NOT_FOUND);
        }
        return commonInvoiceMethod(cartList, customer, address, paymentMethod);
    }

    @Override
    public ResponseEntity<MessageDTO> orderPartialProducts(Long addressId, PartialProductsOrderDTO partialOrderDTO, String email) {
        Customer customer = customerRepository.findByEmail(email);
        Address address = addressRepository.findById(addressId).orElse(null);
        ResponseEntity<MessageDTO> addressValidationResponse = validateAddress(customer, address);
        if (addressValidationResponse != null) {
            return addressValidationResponse;
        }

        List<Cart> cartList = new ArrayList<>();
        for (Long variationId : partialOrderDTO.getVariationIdList()) {
            Cart cart = cartRepository.fetchCartByCustomerAndVariation(customer.getId(), variationId);
            cartList.add(cart);
        }
        if (cartList.size() == 0) {
            throw new EcommerceException(ErrorCode.CART_NOT_FOUND);
        }
        return commonInvoiceMethod(cartList, customer, address, partialOrderDTO.getPaymentMethod());
    }

    @Override
    public ResponseEntity<MessageDTO> directOrder(DirectOrderDTO directOrderDTO, String email) {
        Customer customer = customerRepository.findByEmail(email);
        Address address = addressRepository.findById(directOrderDTO.getAddressId()).orElse(null);
        ResponseEntity<MessageDTO> addressValidationResponse = validateAddress(customer, address);
        if (addressValidationResponse != null) {
            return addressValidationResponse;
        }
        Cart cart = cartRepository.fetchCartByCustomerAndVariation(customer.getId(), directOrderDTO.getVariationId());
        ProductVariation productVariation = variationRepository.findById(directOrderDTO.getVariationId()).orElse(null);
        ResponseEntity<MessageDTO> variationValidationResponse = variationValidation(productVariation, cart);
        if (variationValidationResponse != null) {
            return variationValidationResponse;
        }
        Double totalAmount = getTotalAmount(cart);
        Invoice invoice = invoiceMapping(customer, totalAmount, address, directOrderDTO.getPaymentMethod());
        invoiceRepository.save(invoice);
        setOrderProduct(cart, invoice);
        messageDTO.setMessage("Order placed successfully with order id:" + invoice.getId());
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<MessageDTO> cancelOrder(Long orderProductId, String email) {
        Customer customer = customerRepository.findByEmail(email);
        OrderProduct orderProduct = orderProductRepository.findById(orderProductId).orElse(null);
        ResponseEntity<MessageDTO> checkOrderCancellableResponse = checkOrderCancellable(customer, orderProduct);
        if (checkOrderCancellableResponse != null) {
            return checkOrderCancellableResponse;
        }

        messageDTO.setMessage("Order with orderId: " + orderProductId + " is cancelled successfully, refund if applicable is initiated");
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MessageDTO> returnOrder(Long orderProductId, String email) {
        Customer customer = customerRepository.findByEmail(email);
        OrderProduct orderProduct = orderProductRepository.findById(orderProductId).orElse(null);
        ResponseEntity<MessageDTO> checkOrderReturnableResponse = checkOrderReturnable(customer, orderProduct);
        if (checkOrderReturnableResponse != null) {
            return checkOrderReturnableResponse;
        }
        messageDTO.setMessage("you have requested for order with orderId:" + orderProduct.getId() + ", please wait for response");
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

    private ResponseEntity<MessageDTO> checkOrderReturnable(Customer customer, OrderProduct orderProduct) {
        if (orderProduct == null) {
            throw new EcommerceException(ErrorCode.NO_ORDER_FOUND);
        }
        if (customer.getId() != orderProduct.getInvoice().getCustomer().getId()) {
            throw new EcommerceException(ErrorCode.NOT_AUTHORISED);
        }
        if (orderProduct.getOrderStatus().getToStatus() == ToStatus.RETURN_REQUESTED) {
            messageDTO.setMessage("Return is already requested");
            return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
        }
        if (!orderProduct.getProductVariation().getProduct().getReturnable() ||
                orderProduct.getOrderStatus().getFromStatus() != FromStatus.DELIVERED) {
            messageDTO.setMessage("Order cannot be returned");
        }
        orderProduct.getOrderStatus().setToStatus(ToStatus.RETURN_REQUESTED);
        orderStatusRepository.save(orderProduct.getOrderStatus());
        return null;
    }

    private ResponseEntity<MessageDTO> checkOrderCancellable(Customer customer, OrderProduct orderProduct) {

        if (orderProduct == null) {
            throw new EcommerceException(ErrorCode.NO_ORDER_FOUND);
        }
        if (customer.getId() != orderProduct.getInvoice().getCustomer().getId()) {
            throw new EcommerceException(ErrorCode.NOT_AUTHORISED);
        }
        if (orderProduct.getOrderStatus().getToStatus() == ToStatus.CANCELLED) {
            messageDTO.setMessage("Order is already cancelled");
            return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
        }
        if (!orderProduct.getProductVariation().getProduct().getCancellable() ||
                orderProduct.getOrderStatus().getFromStatus() != FromStatus.ORDER_PLACED) {
            messageDTO.setMessage("Order cannot be cancelled ");
            return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
        }
        orderProduct.getOrderStatus().setToStatus(ToStatus.CANCELLED);
        orderProduct.getOrderStatus().setTransitionComment("Order is cancelled by the customer");
        int newQuantity = orderProduct.getQuantity() + orderProduct.getProductVariation().getQuantityAvailable();
        orderProduct.getProductVariation().setQuantityAvailable(newQuantity);
        variationRepository.save(orderProduct.getProductVariation());
        orderStatusRepository.save(orderProduct.getOrderStatus());
        return null;
    }

    private ResponseEntity<MessageDTO> commonInvoiceMethod(List<Cart> cartList, Customer customer, Address address, String paymentMethod) {
        Double totalAmount = 0.0;
        for (Cart cart : cartList) {
            ProductVariation productVariation = variationRepository.findById(cart.getProductVariation().getId()).orElse(null);
            ResponseEntity<MessageDTO> variationValidationResponse = variationValidation(productVariation, cart);
            if (variationValidationResponse != null) {
                return variationValidationResponse;
            }
            totalAmount += getTotalAmount(cart);
        }
        Invoice invoice = invoiceMapping(customer, totalAmount, address, paymentMethod);
        invoiceRepository.save(invoice);
        mapOrderProductAndStatus(cartList, invoice);
        messageDTO.setMessage("Order placed successfully with order id:" + invoice.getId());
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }


    private ResponseEntity<MessageDTO> validateAddress(Customer customer, Address address) {
        if (address == null) {
            throw new EcommerceException(ErrorCode.NO_ADDRESS_FOUND);
        }
        if (customer.getId() != address.getUser().getId()) {
            throw new EcommerceException(ErrorCode.ADDRESS_NOT_ADDED);
        }
        return null;
    }

    private ResponseEntity<MessageDTO> variationValidation(ProductVariation productVariation, Cart cart) {
        if (productVariation == null || productVariation.getDeleted() || productVariation.getProduct().getDeleted()) {
            messageDTO.setMessage("ProductVariation with id" + ":" + productVariation.getId() + " is not present in the database");
            return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
        }
        if (!productVariation.getActive() || !productVariation.getProduct().getActive()) {
            messageDTO.setMessage("ProductVariation with id" + ":" + productVariation.getId() + " is not active currently");
            return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
        }
        if (cart.getQuantity() > productVariation.getQuantityAvailable()) {
            messageDTO.setMessage("We have currently only " + productVariation.getQuantityAvailable() + " quantities available for variation:" +
                    productVariation.getId());
            return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    private Double getTotalAmount(Cart cart) {
        Double totalAmount = 0.0;
        ProductVariation productVariation = variationRepository.findById(cart.getProductVariation().getId()).get();
        totalAmount = (cart.getQuantity() * productVariation.getPrice());
        int remainingQuantity = productVariation.getQuantityAvailable() - cart.getQuantity();
        productVariation.setQuantityAvailable(remainingQuantity);
        return totalAmount;
    }

    private Invoice invoiceMapping(Customer customer, Double totalAmount, Address address, String paymentMethod) {
        Invoice invoice = new Invoice();
        invoice.setAmountPaid(totalAmount);
        invoice.setCustomer(customer);
        PaymentMethod validatedPaymentMethod = customValidation.verifyPaymentMethod(paymentMethod);
        invoice.setPaymentMethod(validatedPaymentMethod);
        OrderAddress orderAddress = new OrderAddress();
        orderAddress.setOrderAddressLine(address.getAddressLine());
        orderAddress.setOrderCity(address.getCity());
        orderAddress.setOrderCountry(address.getCountry());
        orderAddress.setOrderState(address.getState());
        orderAddress.setOrderLabel(address.getLabel());
        orderAddress.setOrderZipCode(address.getZipCode());
        invoice.setOrderAddress(orderAddress);
        return invoice;
    }

    private void mapOrderProductAndStatus(List<Cart> cartList, Invoice invoice) {
        for (Cart cart : cartList) {
            setOrderProduct(cart, invoice);
            /*cartRepository.delete(cart);*/
        }
    }


    private void setOrderProduct(Cart cart, Invoice invoice) {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setInvoice(invoice);
        orderProduct.setProductVariation(cart.getProductVariation());
        orderProduct.setQuantity(cart.getQuantity());
        orderProduct.setPrice(cart.getQuantity() * cart.getProductVariation().getPrice());
        orderProductRepository.save(orderProduct);
        setOrderStatus(orderProduct);
    }

    private void setOrderStatus(OrderProduct orderProduct) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderProduct(orderProduct);
        orderStatus.setFromStatus(FromStatus.ORDER_PLACED);
        orderStatus.setToStatus(ToStatus.WAITING_FOR_CONFIRMATION);
        orderStatus.setTransitionComment("Order has been placed, waiting for confirmation from the seller");
        orderStatusRepository.save(orderStatus);
    }
}
