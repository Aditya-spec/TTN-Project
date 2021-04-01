package com.Bootcamp.Project.Application.controllers;

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

    @PostMapping(path = "/generate/token")
    public ResponseEntity<String> generatePassword(@RequestParam String email) {
        if (passwordImpl.generatePassword(email)) {
            return new ResponseEntity("email has been sent to reset the password", HttpStatus.OK);
        }
        return new ResponseEntity("not able to generate token", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/reset")
    public ResponseEntity<String> resetPassword( @RequestBody @Valid PasswordTokenDTO passwordTokenDto) {
        if (!passwordImpl.checkPassword(passwordTokenDto)) {
            return new ResponseEntity<>("Passwords don't match", HttpStatus.BAD_REQUEST);
        }
        if (passwordImpl.setNewPassword(passwordTokenDto)) {
            return new ResponseEntity<>("password updated successfully", HttpStatus.OK);
        } else
            return new ResponseEntity<>(" Token is not valid", HttpStatus.BAD_REQUEST);
    }
}
