package com.Bootcamp.Project.Application.controllers;

import com.Bootcamp.Project.Application.dtos.ChangeOrderStatusDTO;
import com.Bootcamp.Project.Application.dtos.MessageDTO;
import com.Bootcamp.Project.Application.dtos.OrderResponseDTO;
import com.Bootcamp.Project.Application.services.InvoiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    InvoiceImpl invoiceImpl;

    @GetMapping("/view-all-orders")
    public List<OrderResponseDTO> viewOrders(@RequestParam("offset") int offset,
                                             @RequestParam("size") int size, HttpServletRequest request){
       return invoiceImpl.viewAllOrders(offset,size,request.getUserPrincipal().getName());
    }

    @PutMapping("/change-status")
    public ResponseEntity<MessageDTO> changeOrderStatus(@Valid @RequestBody ChangeOrderStatusDTO changeOrderStatusDTO, HttpServletRequest request){
        return invoiceImpl.changeOrderStatus(changeOrderStatusDTO,request.getUserPrincipal().getName());
    }


}
