package com.Bootcamp.Project.Application.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenService {
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    public void saveToken(ConfirmationToken token){
        confirmationTokenRepository.save(token);
    }
}
