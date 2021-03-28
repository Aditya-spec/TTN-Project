package com.Bootcamp.Project.Application.dtos;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public abstract class UserDto extends BaseDomainDto {
    private NameDto name;
    private String email;
    private boolean active;
    private boolean deleted;
    private String password;
    private List<AddressDto> addressDtoList;
    private List<RoleDto> roleDtoList;

    public NameDto getName() {
        return name;
    }

    public void setName(NameDto name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<AddressDto> getAddressDtoList() {
        return addressDtoList;
    }

    public void setAddressDtoList(List<AddressDto> addressDtoList) {
        this.addressDtoList = addressDtoList;
    }

    public List<RoleDto> getRoleDtoList() {
        return roleDtoList;
    }

    public void setRoleDtoList(List<RoleDto> roleDtoList) {
        this.roleDtoList = roleDtoList;
    }
}
