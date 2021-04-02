package com.Bootcamp.Project.Application.springSecurity;

import com.Bootcamp.Project.Application.dtos.LoginDTO;
import com.Bootcamp.Project.Application.entities.User;
import com.Bootcamp.Project.Application.enums.ErrorCode;
import com.Bootcamp.Project.Application.exceptionHandling.EcommerceException;
import com.Bootcamp.Project.Application.repositories.AuthenticatedTokenRepository;
import com.Bootcamp.Project.Application.repositories.UserRepository;
import com.Bootcamp.Project.Application.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public class AuthorisationService {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    AuthenticatedTokenRepository authenticatedTokenRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenProvider tokenProvider;
    @Autowired
    UserService userService;

    public ResponseEntity getAuthentication(LoginDTO loginDTO, HttpServletResponse response) {
        User user = userRepository.findByEmail(loginDTO.getEmail());

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            if (user != null) {
                if (user.getActive() && user.isAccountNonLocked()) {
                    if (user.getFailedAttempt() < userService.MAX_FAILED_ATTEMPTS - 1) {
                        userService.increaseFailedAttempts(user);
                        throw new EcommerceException(ErrorCode.PASSWORD_NOT_CORRECT);
                    } else {
                        userService.lock(user);
                        throw new EcommerceException(ErrorCode.USER_IS_LOCKED);
                    }
                } else if (!user.isAccountNonLocked()) {
                    if (userService.unlockWhenTimeExpired(user)) {
                        throw new EcommerceException(ErrorCode.PASSWORD_NOT_CORRECT);
                    } else {
                        throw new EcommerceException(ErrorCode.USER_IS_LOCKED);
                    }
                } else {
                    throw new EcommerceException(ErrorCode.USER_NOT_ACTIVE);
                }
            } else {
                throw new EcommerceException(ErrorCode.USER_NOT_FOUND);
            }
        } else {
            if (user.getFailedAttempt() > 0) {
                userService.resetFailedAttempts(user.getEmail());
            
            }

        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
        Authentication authentication = null;

        authentication = authenticationManager.authenticate(authenticationToken);
        String token = tokenProvider.createToken(loginDTO.getEmail());
        response.addHeader("Authorization", "Bearer " + token);

        AuthenticatedToken oldTokenEntry = authenticatedTokenRepository.findByUsername(loginDTO.getEmail());
        if (oldTokenEntry != null) {
            authenticatedTokenRepository.deleteById(oldTokenEntry.getId());
        }
        AuthenticatedToken newTokenEntry = new AuthenticatedToken();
        newTokenEntry.setToken(token);
        newTokenEntry.setUsername(loginDTO.getEmail());
        authenticatedTokenRepository.save(newTokenEntry);
        return ResponseEntity.ok(token);
    }
    }

