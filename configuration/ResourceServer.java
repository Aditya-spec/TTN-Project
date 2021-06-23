package com.Bootcamp.Project.Application.configuration;


import com.Bootcamp.Project.Application.configuration.filters.FilterExceptionHandler;
import com.Bootcamp.Project.Application.configuration.filters.JwtFilter;
import com.Bootcamp.Project.Application.dtos.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
@EnableWebSecurity
public class ResourceServer extends WebSecurityConfigurerAdapter {

    @Value("${jwt.secret}")
    String secret;
    @Value("${jwt.duration}")
    Integer duration;


    @Autowired
    AppUserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public TokenProvider tokenProvider() {
        return new TokenProvider(secret, duration);
    }

    @Bean
    public MessageDTO returnMessageDTO(){
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateFormat = simpleDateFormat.format(date);
        return new MessageDTO(dateFormat);
    }


    @Bean
    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return new SpringSecurityAuditorAware();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public OncePerRequestFilter oncePerRequestFilter() {
        return new FilterExceptionHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/register-page/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/password/**").permitAll()
                .antMatchers("/oauth").permitAll()
                .antMatchers("/order/**").permitAll()
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/seller/**/**").hasAnyRole("SELLER")
                .antMatchers("/customer/**/**").hasAnyRole("CUSTOMER")
                .antMatchers("/doLogout").hasAnyRole("ADMIN", "SELLER", "CUSTOMER")
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .csrf().disable();
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
