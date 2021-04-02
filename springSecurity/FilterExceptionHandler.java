package com.Bootcamp.Project.Application.springSecurity;

import com.Bootcamp.Project.Application.exceptionHandling.EcommerceException;
import com.Bootcamp.Project.Application.exceptionHandling.ExceptionResponse;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class FilterExceptionHandler extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(getClass());


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        }
        catch (EcommerceException e) {
            response.setContentType("application/json");
            response.setStatus(e.errorCode.getCode());
            ExceptionResponse apiResponse = new ExceptionResponse(new Date(),e.errorCode.getErrorDesc(), e.errorCode.getStatusCode());
            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        }
    }
}