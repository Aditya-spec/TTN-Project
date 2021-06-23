package com.Bootcamp.Project.Application.dtos;

import java.util.List;

public class RoleDTO {
    private String authorization;
    private List<UserDTO> userDtoList;

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public List<UserDTO> getUserDtoList() {
        return userDtoList;
    }

    public void setUserDtoList(List<UserDTO> userDtoList) {
        this.userDtoList = userDtoList;
    }
}
