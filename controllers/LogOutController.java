/*
package com.Bootcamp.Project.Application.controllers;

import com.Bootcamp.Project.Application.repositories.LoggedInTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LogOutController {

    @Autowired
    LoggedInTokenRepository loggedInTokenRepository;

    @GetMapping("/doLogout")
    public String logout(HttpServletRequest httpServletRequest) {
        String authorization = httpServletRequest.getHeader("Authorization");
        String token = null;
        token = authorization.substring(7);
        String username = httpServletRequest.getUserPrincipal().getName();

        LoggedInToken loggedInToken = loggedInTokenRepository.findByUsername(username);
        loggedInTokenRepository.deleteById(loggedInToken.getId());
        return "logout Successfully";
    }


    @GetMapping("/")
    public String index(){
        return "index";
    }

}
*/
