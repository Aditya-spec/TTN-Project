package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.dtos.PasswordTokenDTO;
import com.Bootcamp.Project.Application.entities.User;
import com.Bootcamp.Project.Application.exceptionHandling.NotFoundException;
import com.Bootcamp.Project.Application.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.UUID;

@Service
public class PasswordService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean generatePassword(String email) {

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new NotFoundException("user with email " + email + " does not exist");
        } else {
            user.setResetToken(UUID.randomUUID().toString());
            user.setResetTokenTime(LocalTime.now().plusMinutes(15));
            userRepository.save(user);
            String body = " Please generate new Password using this link" +
                    " which will be valid for 15 minutes only = \n http://localhost:8080/password/reset/" + user.getResetToken();
            emailService.sendMail(user.getEmail(), "Password Generation Link", body);
            return true;
        }
    }

    public Boolean checkPassword(PasswordTokenDTO passwordTokenDto) {
        if (passwordTokenDto.getPassword().equals(passwordTokenDto.getConfirmPassword()))
            return true;
        else return false;
    }

    public Boolean setNewPassword(PasswordTokenDTO passwordTokenDto) {
        User user = userRepository.findByResetToken(passwordTokenDto.getToken());
        if (user == null) {
            user.setActive(false);
            return false;
        }
        LocalTime currentTime = LocalTime.now();
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
