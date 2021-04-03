package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.repositories.AuthenticatedTokenRepository;
import com.Bootcamp.Project.Application.entities.AuthenticatedToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class LogoutService {

    @Autowired
    AuthenticatedTokenRepository authenticatedTokenRepository;

    public String logout(HttpServletRequest httpServletRequest) {
        String authorization = httpServletRequest.getHeader("Authorization");
        String token = null;
        token = authorization.substring(7);
        String username = httpServletRequest.getUserPrincipal().getName();

        AuthenticatedToken loggedInToken = authenticatedTokenRepository.findByUsername(username);
        authenticatedTokenRepository.deleteById(loggedInToken.getId());
        return "logout Successfully";
    }
}
