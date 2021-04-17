package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.dtos.*;
import com.Bootcamp.Project.Application.entities.*;
import com.Bootcamp.Project.Application.enums.*;
import com.Bootcamp.Project.Application.exceptionHandling.EcommerceException;
import com.Bootcamp.Project.Application.repositories.*;
import com.Bootcamp.Project.Application.services.serviceInterfaces.InvoiceService;
import com.Bootcamp.Project.Application.validations.CustomValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    SellerRepository sellerRepository;
    @Autowired
    MessageDTO messageDTO;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CustomValidation customValidation;
    @Autowired
    PaginationImpl paginationImpl;

    Logger logger = LoggerFactory.getLogger(getClass().getName());

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

    @Override
    public OrderResponseDTO viewOrder(Long orderId, String email) {
        Customer customer = customerRepository.findByEmail(email);
        Invoice invoice = invoiceRepository.findById(orderId).orElse(null);
        if (invoice == null) {
            throw new EcommerceException(ErrorCode.ORDER_NOT_FOUND);
        }
        if (customer.getId() != invoice.getCustomer().getId()) {
            throw new EcommerceException(ErrorCode.NOT_AUTHORISED);
        }
        OrderResponseDTO responseDTO = new OrderResponseDTO();
        showInvoiceAddressMapping(invoice, responseDTO);
        Pageable pageable = paginationImpl.pagination(0, 0);
        List<OrderProduct> orderProductList = orderProductRepository.fetchByOrderId(orderId, pageable);
        if (orderProductList.size() == 0) {
            throw new EcommerceException(ErrorCode.NO_DATA);
        }
        getOrderProductDTOList(orderProductList);
        responseDTO.setOrderProductDTOList(getOrderProductDTOList(orderProductList));
        return responseDTO;

    }


    @Override
    public List<OrderResponseDTO> viewAllOrders(int offset, int size, String email) {
        User user = userRepository.findByEmail(email);
        if (user.getRoles().size() == 3)
            return adminViewOrders(email, paginationImpl.pagination(offset, size));
        String role = user.getRoles()
                .stream()
                .map(e -> e.getAuthorization())
                .findFirst()
                .get();
        if (role.equals("ROLE_SELLER"))
            return sellerViewOrders(email, paginationImpl.pagination(offset, size));
        return customerViewOrders(email, paginationImpl.pagination(offset, size));
    }

    @Override
    public ResponseEntity<MessageDTO> changeOrderStatus(ChangeOrderStatusDTO changeOrderStatusDTO, String email) {
        User user = userRepository.findByEmail(email);
        if (user.getRoles().size() == 3)
            return adminChangeStatus(email, changeOrderStatusDTO);
        String role = user.getRoles()
                .stream()
                .map(e -> e.getAuthorization())
                .findFirst()
                .get();
        if (role.equals("ROLE_SELLER"))
            return sellerChangeStatus(email, changeOrderStatusDTO);
        throw new EcommerceException(ErrorCode.NOT_AUTHORISED);
    }

    private ResponseEntity<MessageDTO> sellerChangeStatus(String email, ChangeOrderStatusDTO changeOrderStatusDTO) {
        Seller seller = sellerRepository.findByEmail(email);
        OrderProduct orderProduct = orderProductRepository.findById(changeOrderStatusDTO.getOrderProductId()).orElse(null);
        if (orderProduct == null) {
            throw new EcommerceException(ErrorCode.NO_ORDER_FOUND);
        }
        if (seller.getId() != orderProduct.getProductVariation().getProduct().getSeller().getId()) {
            throw new EcommerceException(ErrorCode.NOT_AUTHORISED);
        }
        return updateStatus(orderProduct, changeOrderStatusDTO);
    }

    private ResponseEntity<MessageDTO> adminChangeStatus(String email, ChangeOrderStatusDTO changeOrderStatusDTO) {
        OrderProduct orderProduct = orderProductRepository.findById(changeOrderStatusDTO.getOrderProductId()).orElse(null);
        if (orderProduct == null) {
            throw new EcommerceException(ErrorCode.NO_ORDER_FOUND);
        }
        return updateStatus(orderProduct, changeOrderStatusDTO);
    }

    private ResponseEntity<MessageDTO> updateStatus(OrderProduct orderProduct, ChangeOrderStatusDTO changeOrderStatusDTO) {
        OrderStatusEnum fromStatus = customValidation.verifyOrderStatus(changeOrderStatusDTO.getFromStatus());
        OrderStatusEnum toStatus = customValidation.verifyOrderStatus(changeOrderStatusDTO.getToStatus());
        List<OrderStatusEnum> customerSpecificStatusList = Arrays.asList(OrderStatusEnum.ORDER_PLACED, OrderStatusEnum.CANCELLED, OrderStatusEnum.RETURN_REQUESTED);
        if(customerSpecificStatusList.contains(toStatus)){
            throw new EcommerceException(ErrorCode.NOT_AUTHORISED);
        }
        if (orderProduct.getOrderStatus().getToStatus() != fromStatus) {
            throw new EcommerceException(ErrorCode.STATUS_CHANGE_INVALID);
        }
        orderProduct.getOrderStatus().setFromStatus(fromStatus);
        for (FromStatus status : toStatus.getFromStatusList()) {
            if (fromStatus.toString().equals(status.toString())) {
                orderProduct.getOrderStatus().setToStatus(toStatus);
                orderStatusRepository.save(orderProduct.getOrderStatus());
                messageDTO.setMessage("Status changed successfully");
                return new ResponseEntity<>(messageDTO, HttpStatus.OK);
            }
        }
        throw new EcommerceException(ErrorCode.STATUS_CHANGE_INVALID);
    }

    private List<OrderResponseDTO> customerViewOrders(String email, Pageable pageable) {
        Customer customer = customerRepository.findByEmail(email);
        List<Invoice> invoiceList = invoiceRepository.fetchByCustomerId(customer.getId(), pageable);
        if (invoiceList.size() == 0) {
            throw new EcommerceException(ErrorCode.NO_DATA);
        }
        List<OrderResponseDTO> orderResponseDTOList = new ArrayList<>();
        for (Invoice invoice : invoiceList) {
            OrderResponseDTO responseDTO = new OrderResponseDTO();
            showInvoiceAddressMapping(invoice, responseDTO);
            List<OrderProduct> orderProductList = orderProductRepository.fetchByOrderId(invoice.getId(), pageable);
            responseDTO.setOrderProductDTOList(getOrderProductDTOList(orderProductList));
            orderResponseDTOList.add(responseDTO);
        }
        return orderResponseDTOList;
    }

    private List<OrderResponseDTO> sellerViewOrders(String email, Pageable pageable) {
        Seller seller = sellerRepository.findByEmail(email);
        List<OrderProduct> orderProductList = orderProductRepository.fetchAllSellerOrders(seller.getId(), pageable);
        if (orderProductList.size() == 0) {
            throw new EcommerceException(ErrorCode.NO_DATA);
        }
        List<OrderResponseDTO> orderResponseDTOList = new ArrayList<>();
        for (OrderProduct orderProduct : orderProductList) {
            OrderResponseDTO responseDTO = new OrderResponseDTO();
            responseDTO.setDateCreated(orderProduct.getInvoice().getDateCreated());
            responseDTO.setPaymentMethod(orderProduct.getInvoice().getPaymentMethod().toString());
            List<OrderProductDTO> orderProductDTOList = getOrderProductDTOList(orderProductList);
            responseDTO.setOrderProductDTOList(orderProductDTOList);
            for (OrderProductDTO orderProductDTO : orderProductDTOList) {
                orderProductDTO.setSellerId(null);
            }
            orderResponseDTOList.add(responseDTO);
        }
        return orderResponseDTOList;
    }

    private List<OrderResponseDTO> adminViewOrders(String email, Pageable pageable) {
        List<OrderProduct> orderProductList = orderProductRepository.fetchAll(pageable);
        if (orderProductList.size() == 0) {
            throw new EcommerceException(ErrorCode.NO_DATA);
        }
        List<OrderResponseDTO> orderResponseDTOList = new ArrayList<>();
        for (OrderProduct orderProduct : orderProductList) {
            OrderResponseDTO responseDTO = new OrderResponseDTO();
            responseDTO.setDateCreated(orderProduct.getInvoice().getDateCreated());
            responseDTO.setPaymentMethod(orderProduct.getInvoice().getPaymentMethod().toString());
            responseDTO.setCustomerId(orderProduct.getInvoice().getCustomer().getId());
            responseDTO.setOrderProductDTOList(getOrderProductDTOList(orderProductList));
            orderResponseDTOList.add(responseDTO);
        }
        return orderResponseDTOList;
    }

    private List<OrderProductDTO> getOrderProductDTOList(List<OrderProduct> orderProductList) {
        List<OrderProductDTO> orderProductDTOList = orderProductList
                .stream()
                .map(e -> showInvoiceOrderProductMapping(e))
                .collect(Collectors.toList());
        return orderProductDTOList;
    }

    private void showInvoiceAddressMapping(Invoice invoice, OrderResponseDTO responseDTO) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setAddressLine(invoice.getOrderAddress().getOrderAddressLine());
        addressDTO.setCity(invoice.getOrderAddress().getOrderCity());
        addressDTO.setState(invoice.getOrderAddress().getOrderState());
        addressDTO.setZipCode(invoice.getOrderAddress().getOrderZipCode());
        addressDTO.setLabel(invoice.getOrderAddress().getOrderLabel().toString());
        addressDTO.setCountry(invoice.getOrderAddress().getOrderCountry());
        responseDTO.setAddress(addressDTO);
        responseDTO.setCustomerId(invoice.getCustomer().getId());
        responseDTO.setCustomerEmail(invoice.getCustomer().getEmail());
        responseDTO.setOrderId(invoice.getId());
        responseDTO.setDateCreated(invoice.getDateCreated());
        responseDTO.setTotalAmount(invoice.getAmountPaid());
        responseDTO.setPaymentMethod(invoice.getPaymentMethod().toString());
    }

    private OrderProductDTO showInvoiceOrderProductMapping(OrderProduct orderProduct) {
        OrderProductDTO orderProductDTO = new OrderProductDTO();
        orderProductDTO.setOrderProductId(orderProduct.getId());
        orderProductDTO.setQuantity(orderProduct.getQuantity());
        orderProductDTO.setPrice(orderProduct.getPrice());
        orderProductDTO.setProductName(orderProduct.getProductVariation().getProduct().getName());
        orderProductDTO.setBrandName(orderProduct.getProductVariation().getProduct().getBrand());
        orderProductDTO.setVariationId(orderProduct.getProductVariation().getId());
        orderProductDTO.setSellerId(orderProduct.getProductVariation().getProduct().getSeller().getId());
        orderProductDTO.setMetaData((List<Object>) orderProduct.getProductVariation().getMetadata().get("metadata"));

        OrderStatusDTO orderStatusDTO = new OrderStatusDTO();
        orderStatusDTO.setToStatus(orderProduct.getOrderStatus().getToStatus().toString());
        orderProductDTO.setOrderStatusDTO(orderStatusDTO);
        return orderProductDTO;
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
            /*cartRepository.delete(cart);*/ //uncomment it
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
        orderStatus.setFromStatus(OrderStatusEnum.ORDER_PLACED);
        orderStatus.setToStatus(OrderStatusEnum.WAITING_FOR_CONFIRMATION);
        orderStatus.setTransitionComment("Order has been placed, waiting for confirmation from the seller");
        orderStatusRepository.save(orderStatus);
    }

    private ResponseEntity<MessageDTO> checkOrderCancellable(Customer customer, OrderProduct orderProduct) {

        if (orderProduct == null) {
            throw new EcommerceException(ErrorCode.NO_ORDER_FOUND);
        }
        if (customer.getId() != orderProduct.getInvoice().getCustomer().getId()) {
            throw new EcommerceException(ErrorCode.NOT_AUTHORISED);
        }
        if (orderProduct.getOrderStatus().getToStatus() == OrderStatusEnum.CANCELLED) {
            messageDTO.setMessage("Order is already cancelled");
            return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
        }
        if (!orderProduct.getProductVariation().getProduct().getCancellable() ||
                orderProduct.getOrderStatus().getFromStatus() != OrderStatusEnum.ORDER_PLACED) {
            messageDTO.setMessage("Order cannot be cancelled ");
            return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
        }
        orderProduct.getOrderStatus().setToStatus(OrderStatusEnum.CANCELLED);
        orderProduct.getOrderStatus().setTransitionComment("Order is cancelled by the customer");
        int newQuantity = orderProduct.getQuantity() + orderProduct.getProductVariation().getQuantityAvailable();
        orderProduct.getProductVariation().setQuantityAvailable(newQuantity);
        variationRepository.save(orderProduct.getProductVariation());
        orderStatusRepository.save(orderProduct.getOrderStatus());
        return null;
    }

    private ResponseEntity<MessageDTO> checkOrderReturnable(Customer customer, OrderProduct orderProduct) {
        if (orderProduct == null) {
            throw new EcommerceException(ErrorCode.NO_ORDER_FOUND);
        }
        if (customer.getId() != orderProduct.getInvoice().getCustomer().getId()) {
            throw new EcommerceException(ErrorCode.NOT_AUTHORISED);
        }
        if (orderProduct.getOrderStatus().getToStatus() == OrderStatusEnum.RETURN_REQUESTED) {
            messageDTO.setMessage("Return is already requested");
            return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
        }
        if (!orderProduct.getProductVariation().getProduct().getReturnable() ||
                orderProduct.getOrderStatus().getFromStatus() != OrderStatusEnum.DELIVERED) {
            messageDTO.setMessage("Order cannot be returned");
        }
        orderProduct.getOrderStatus().setToStatus(OrderStatusEnum.RETURN_REQUESTED);
        orderStatusRepository.save(orderProduct.getOrderStatus());
        return null;
    }
}
