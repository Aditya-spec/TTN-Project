package com.Bootcamp.Project.Application.services.serviceInterfaces;

import com.Bootcamp.Project.Application.dtos.MessageDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    ResponseEntity<MessageDTO> uploadImage(MultipartFile file,String email) throws IOException;

    ResponseEntity<MessageDTO> uploadVariationImage(MultipartFile file, long variationId, String email) throws IOException;
}
