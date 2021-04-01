package com.Bootcamp.Project.Application.services.serviceInterfaces;

import com.Bootcamp.Project.Application.dtos.PasswordTokenDTO;

public interface PasswordService {
    public boolean generatePassword(String email);
    public Boolean checkPassword(PasswordTokenDTO passwordTokenDto);
    public Boolean setNewPassword(PasswordTokenDTO passwordTokenDto);
}
