package com.Bootcamp.Project.Application.dtos;

import java.util.List;

public class CMDResponseDTO {
    private String name;
    private List<String> values;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
