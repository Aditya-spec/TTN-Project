package com.Bootcamp.Project.Application.configuration;

import com.Bootcamp.Project.Application.entities.*;
import com.Bootcamp.Project.Application.enums.Label;
import com.Bootcamp.Project.Application.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class Bootstrap implements ApplicationRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        /*Name name1=new Name();
        name1.setFirstName("Customer");
        name1.setLastName("Singh");

        Customer customer=new Customer();
        customer.setEmail("Customer@gmail.com");
        customer.setPassword(bCryptPasswordEncoder.encode("Customer@123"));
        List<Role> roleList=new ArrayList<>();
        roleList.add(new Role("ROLE_CUSTOMER"));
        customer.setActive(true);
        customer.setDeleted(false);
        customer.setRoles(roleList);
        customer.setName(name1);
        userRepository.save(customer);

        Name name2=new Name();
        name2.setFirstName("Seller");
        name2.setLastName("Singh");

        Seller seller=new Seller();
        seller.setEmail("Seller@gmail.com");
        seller.setPassword(bCryptPasswordEncoder.encode("Seller@123"));
        List<Role> roleList1=new ArrayList<>();
        roleList.add(new Role("ROLE_SELLER"));
        seller.setActive(true);
        seller.setDeleted(false);
        seller.setRoles(roleList);
        seller.setName(name2);
        userRepository.save(seller);*/

       /* if (userRepository.count() <= 0) {
            Admin admin1=new Admin();
            admin1.setEmail("adminuser1@gmail.com");
            admin1.setPassword(bCryptPasswordEncoder.encode("Admin1@user"));
            List<Role> roles1 = new LinkedList<>();
            roles1.add(new Role("ROLE_SELLER"));
            roles1.add(new Role("ROLE_ADMIN"));
            roles1.add(new Role("ROLE_CUSTOMER"));
            admin1.setActive(true);
            admin1.setDeleted(false);
            admin1.setRoles(roles1);

            Admin admin2=new Admin();
            admin2.setEmail("adminuser2@gmail.com");
            admin2.setPassword(bCryptPasswordEncoder.encode("Admin2@user"));
            List<Role> roles2 = new LinkedList<>();
            roles2.add(new Role("ROLE_SELLER"));
            roles2.add(new Role("ROLE_ADMIN"));
            roles2.add(new Role("ROLE_CUSTOMER"));
            admin2.setActive(true);
            admin2.setDeleted(false);
            admin2.setRoles(roles2);

            userRepository.save(admin1);
            userRepository.save(admin2);
            System.out.println("Total Users:"+ userRepository.count());
        }*/


    }
}
