package com.Bootcamp.Project.Application.configuration;

import com.Bootcamp.Project.Application.entities.Role;
import com.Bootcamp.Project.Application.entities.User;
import com.Bootcamp.Project.Application.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

import java.util.List;


@Repository
public class UserDao {
    @Autowired
    private UserRepository userRepository;

    AppUser loadUserByEmail(String email) throws Exception {


        User user = userRepository.findByEmail(email);
        if (user != null) {

            List<Role> rolesList = user.getRoles();

            List<GrantAuthorityImpl> grantAuthorities = new ArrayList<>();
            for (Role roles : rolesList) {
                System.out.println(roles.getAuthorization());
                grantAuthorities.add(new GrantAuthorityImpl(roles.getAuthorization()));
            }
            /*return new AppUser(user.getEmail(), user.getPassword(), user.getActive(), user.getDeleted(), grantAuthorities);*/
            return new AppUser(user.getEmail(), user.getPassword(), user.getActive(),  grantAuthorities);
        } else {
            System.out.println();
            throw new RuntimeException();

        }

    }
}