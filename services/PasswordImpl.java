package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.dtos.PasswordTokenDTO;
import com.Bootcamp.Project.Application.entities.User;
import com.Bootcamp.Project.Application.enums.ErrorCode;
import com.Bootcamp.Project.Application.exceptionHandling.EcommerceException;
import com.Bootcamp.Project.Application.repositories.UserRepository;
import com.Bootcamp.Project.Application.services.serviceInterfaces.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordImpl implements PasswordService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean generatePassword(String email) {

        User user = userRepository.findByEmail(email);
        if (user == null) {
           throw new EcommerceException(ErrorCode.USER_NOT_FOUND);

        }
        if(!user.getActive()){
            throw new EcommerceException(ErrorCode.NOT_ACTIVE);
        }
            user.setResetToken(UUID.randomUUID().toString());
            user.setResetTokenTime(LocalDateTime.now().plusMinutes(15));
            userRepository.save(user);
            String body = " Please generate new Password using this link" +
                    " which will be valid for 15 minutes only = \n http://localhost:8080/password/reset?token=" + user.getResetToken();
            emailService.sendMail(user.getEmail(), "Password Generation Link", body);
            System.out.println(body);
            return true;

    }

    public Boolean checkPassword(PasswordTokenDTO passwordTokenDto) {
        if (passwordTokenDto.getPassword().equals(passwordTokenDto.getConfirmPassword()))
            return true;
        else return false;
    }

    public Boolean setNewPassword(String token, PasswordTokenDTO passwordTokenDto) {
        User user = userRepository.findByResetToken(token);
        if (user == null) {
            user.setActive(false);
            return false;
        }
        LocalDateTime currentTime = LocalDateTime.now();
        if (currentTime.isAfter(user.getResetTokenTime())) {
            user.setActive(false);
            return false;
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(passwordTokenDto.getPassword()));
            user.setResetToken(null);
            user.setResetTokenTime(null);
            user.setActive(true);
            userRepository.save(user);
            return true;
        }
    }
}
