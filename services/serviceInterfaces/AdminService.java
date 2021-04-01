package com.Bootcamp.Project.Application.services.serviceInterfaces;

import com.Bootcamp.Project.Application.dtos.RegisteredCustomerDTO;
import com.Bootcamp.Project.Application.dtos.RegisteredSellerDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface AdminService {
    public List<RegisteredCustomerDTO> getCustomers();

    public List<RegisteredSellerDTO> getSellers();

    public ResponseEntity<String> activateUser(Long id, Map<Object, Object> fields);

    public ResponseEntity<String> deactivateUser(Long id, Map<Object, Object> fields);
}
