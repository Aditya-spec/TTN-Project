package com.Bootcamp.Project.Application.configuration;

import com.Bootcamp.Project.Application.entities.Role;

import com.Bootcamp.Project.Application.entities.User;
import com.Bootcamp.Project.Application.enums.ErrorCode;
import com.Bootcamp.Project.Application.exceptionHandling.EcommerceException;
import com.Bootcamp.Project.Application.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {

    @Autowired
    UserRepository userRepository;

    AppUser loadUserByUsername(String email) {

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new EcommerceException(ErrorCode.USER_NOT_FOUND);
        }
        if(!user.getActive()){
            throw new EcommerceException(ErrorCode.USER_NOT_ACTIVE);
        }

        if (email != null) {
            List<Role> rolesList = user.getRoles();

            List<GrantAuthorityImpl> grantAuthorities = new ArrayList<>();
            for (Role roles : rolesList) {
                System.out.println(roles.getAuthorization());
                grantAuthorities.add(new GrantAuthorityImpl(roles.getAuthorization()));
            }
            /*return new AppUser(user.getEmail(), user.getPassword(), user.getActive(), user.getDeleted(), grantAuthorities);*/
            return new AppUser(user,   grantAuthorities);
        } else {
            throw new RuntimeException();
        }
    }


}
