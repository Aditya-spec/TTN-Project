package com.Bootcamp.Project.Application.controllers;

import com.Bootcamp.Project.Application.repositories.AuthenticatedTokenRepository;
import com.Bootcamp.Project.Application.services.LogoutService;
import com.Bootcamp.Project.Application.springSecurity.AuthenticatedToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LogOutController {

    @Autowired
    LogoutService logoutService;

    @GetMapping("/doLogout")
    public String logout(HttpServletRequest httpServletRequest) {
        return logoutService.logout(httpServletRequest);
    }


    @GetMapping("/")
    public String index(){
        return "index";
    }

}
