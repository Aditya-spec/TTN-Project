package com.Bootcamp.Project.Application.controllers;

import com.Bootcamp.Project.Application.dtos.LoginDTO;
import com.Bootcamp.Project.Application.dtos.MessageDTO;
import com.Bootcamp.Project.Application.services.AuthorisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class LoginController {

    @Autowired
    AuthorisationService authorisationService;
    @Autowired
    MessageDTO messageDTO;

    @PostMapping("/oauth")
    public ResponseEntity authenticate(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        return authorisationService.getAuthentication(loginDTO, response);
    }
}
