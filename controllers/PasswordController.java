package com.Bootcamp.Project.Application.controllers;

import com.Bootcamp.Project.Application.dtos.MessageDTO;
import com.Bootcamp.Project.Application.dtos.PasswordTokenDTO;
import com.Bootcamp.Project.Application.services.PasswordImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/password")
public class PasswordController {
    @Autowired
    PasswordImpl passwordImpl;
    @Autowired
    MessageDTO messageDTO;

    @PostMapping(path = "/generate-token")
    public ResponseEntity<MessageDTO> generatePassword(@RequestParam String email) {
        if (passwordImpl.generatePassword(email)) {
            messageDTO.setMessage("email has been sent to reset the password");
            return new ResponseEntity(messageDTO, HttpStatus.OK);
        }
        messageDTO.setMessage("not able to generate token");
        return new ResponseEntity(messageDTO, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/reset")
    public ResponseEntity<MessageDTO> resetPassword(@RequestParam String token, @RequestBody @Valid PasswordTokenDTO passwordTokenDto) {
        if (!passwordImpl.checkPassword(passwordTokenDto)) {
            messageDTO.setMessage("Passwords don't match");
            return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
        }
        if (passwordImpl.setNewPassword(token,passwordTokenDto)) {
           messageDTO.setMessage("password updated successfully");
            return new ResponseEntity<>(messageDTO, HttpStatus.OK);
        } else
            messageDTO.setMessage(" Token is not valid");
            return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
    }
}
