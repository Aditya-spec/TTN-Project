package com.Bootcamp.Project.Application.dtos;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public abstract class UserDTO extends BaseDomainDTO {
    private NameDTO name;
    private String email;
    private boolean active;
    private boolean deleted;
    private String password;
    private List<AddressDTO> addressDTOList;
    private List<RoleDTO> roleDTOList;

    public NameDTO getName() {
        return name;
    }

    public void setName(NameDTO name) {
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

    public List<AddressDTO> getAddressDtoList() {
        return addressDTOList;
    }

    public void setAddressDtoList(List<AddressDTO> addressDTOList) {
        this.addressDTOList = addressDTOList;
    }

    public List<RoleDTO> getRoleDtoList() {
        return roleDTOList;
    }

    public void setRoleDtoList(List<RoleDTO> roleDTOList) {
        this.roleDTOList = roleDTOList;
    }
}
