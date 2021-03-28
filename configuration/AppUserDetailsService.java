package com.Bootcamp.Project.Application.configuration;


import com.Bootcamp.Project.Application.exceptionHandling.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        System.out.println("Trying to authenticate user ::" + email);

        try {
            return userDao.loadUserByEmail(email);
        } catch (Exception e) {
            throw new NotFoundException("User not found with email: " + email);
        }
    }


    /*https://dzone.com/articles/spring-boot-custom-password-validator-using-passay
    https://stackabuse.com/spring-custom-password-validation/
    Madhav Khanna3:18 PM
    https://dzone.com/articles/creating-custom-annotations-in-java
    https://www.baeldung.com/java-custom-annotation*/
}