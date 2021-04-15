package com.Bootcamp.Project.Application.services.serviceInterfaces;

import com.Bootcamp.Project.Application.dtos.DirectOrderDTO;
import com.Bootcamp.Project.Application.dtos.MessageDTO;
import com.Bootcamp.Project.Application.dtos.PartialProductsOrderDTO;
import org.springframework.http.ResponseEntity;

public interface InvoiceService {
    ResponseEntity<MessageDTO> orderAllProducts(Long addressID, String paymentMethod, String email);

    ResponseEntity<MessageDTO> orderPartialProducts(Long addressId, PartialProductsOrderDTO partialProductsOrderDTO, String email);

    ResponseEntity<MessageDTO> directOrder(DirectOrderDTO directOrderDTO, String email);

    ResponseEntity<MessageDTO> cancelOrder(Long orderProductId, String email);

    ResponseEntity<MessageDTO> returnOrder(Long orderProductId, String email);
}
