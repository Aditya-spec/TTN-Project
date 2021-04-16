package com.Bootcamp.Project.Application.controllers;

import com.Bootcamp.Project.Application.dtos.OrderResponseDTO;
import com.Bootcamp.Project.Application.services.InvoiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    InvoiceImpl invoiceImpl;

    @GetMapping("view-all-orders")
    public List<OrderResponseDTO> viewOrders(@RequestParam("offset") int offset,
                                             @RequestParam("size") int size, HttpServletRequest request){
       return invoiceImpl.viewAllOrders(offset,size,request.getUserPrincipal().getName());
    }
}
