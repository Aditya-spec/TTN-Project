package com.Bootcamp.Project.Application.services.serviceInterfaces;

import com.Bootcamp.Project.Application.dtos.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface InvoiceService {
    ResponseEntity<MessageDTO> orderAllProducts(Long addressID, String paymentMethod, String email);

    ResponseEntity<MessageDTO> orderPartialProducts(Long addressId, PartialProductsOrderDTO partialProductsOrderDTO, String email);

    ResponseEntity<MessageDTO> directOrder(DirectOrderDTO directOrderDTO, String email);

    ResponseEntity<MessageDTO> cancelOrder(Long orderProductId, String email);

    ResponseEntity<MessageDTO> returnOrder(Long orderProductId, String email);

    OrderResponseDTO viewOrder(Long orderId, String email);

    List<OrderResponseDTO> viewAllOrders(int offset, int size, String email);

    ResponseEntity<MessageDTO> changeOrderStatus(ChangeOrderStatusDTO changeOrderStatusDTO, String email
    );
}
