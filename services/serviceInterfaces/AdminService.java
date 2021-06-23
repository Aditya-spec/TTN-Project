package com.Bootcamp.Project.Application.services.serviceInterfaces;

import com.Bootcamp.Project.Application.dtos.MessageDTO;
import com.Bootcamp.Project.Application.dtos.RegisteredCustomerDTO;
import com.Bootcamp.Project.Application.dtos.RegisteredSellerDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface AdminService {
    public List<RegisteredCustomerDTO> getCustomers(int offset, int size);

    public List<RegisteredSellerDTO> getSellers(int offset, int size);

    public ResponseEntity<MessageDTO> activateUser(Long id, Map<Object, Object> fields);

    public ResponseEntity<MessageDTO> deactivateUser(Long id, Map<Object, Object> fields);
}
