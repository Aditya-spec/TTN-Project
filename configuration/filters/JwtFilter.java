package com.Bootcamp.Project.Application.configuration.filters;

import com.Bootcamp.Project.Application.configuration.AppUserDetailsService;
import com.Bootcamp.Project.Application.configuration.TokenProvider;
import com.Bootcamp.Project.Application.entities.AuthenticatedToken;
import com.Bootcamp.Project.Application.enums.ErrorCode;
import com.Bootcamp.Project.Application.exceptionHandling.EcommerceException;

import com.Bootcamp.Project.Application.repositories.AuthenticatedTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    AppUserDetailsService userDetailsService;

    @Autowired
    AuthenticatedTokenRepository authenticatedTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorization = httpServletRequest.getHeader("Authorization");
        String token = null;
        String userName = null;

        if (null != authorization && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7);
            userName = tokenProvider.getUsernameFromToken(token);
        }

        if (null != userName && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails
                    = userDetailsService.loadUserByUsername(userName);

            if (tokenProvider.validateToken(token, userDetails)) {

                AuthenticatedToken authenticatedToken = authenticatedTokenRepository.findByUsername(userName);
                if (authenticatedToken == null) {
                    throw new EcommerceException(ErrorCode.INVALID_TOKEN);
                }

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
                );

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                throw new EcommerceException(ErrorCode.INVALID_TOKEN);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}