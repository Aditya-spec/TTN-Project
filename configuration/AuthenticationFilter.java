/*
package com.Bootcamp.Project.Application.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class AuthenticationFilter extends HttpFilter{

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        System.out.println("hello");


    }

    @Override
    public void destroy() {

    }
    @Bean
    public FilterRegistrationBean<AuthenticationFilter> filter() {
        FilterRegistrationBean<AuthenticationFilter> bean = new FilterRegistrationBean<>();

        bean.setFilter(new AuthenticationFilter());
        bean.addUrlPatterns("/oauth/token");  // or use setUrlPatterns()

        return bean;
    }


}
*/
