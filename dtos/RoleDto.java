package com.Bootcamp.Project.Application.dtos;

import java.util.List;

public class RoleDto {
    private String authorization;
    private List<UserDto> userDtoList;

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public List<UserDto> getUserDtoList() {
        return userDtoList;
    }

    public void setUserDtoList(List<UserDto> userDtoList) {
        this.userDtoList = userDtoList;
    }
}
