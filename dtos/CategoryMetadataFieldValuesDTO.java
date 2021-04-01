package com.Bootcamp.Project.Application.dtos;

import org.json.simple.JSONArray;

public class CategoryMetadataFieldValuesDTO {
    private JSONArray fieldValues;

    public JSONArray getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(JSONArray fieldValues) {
        this.fieldValues = fieldValues;
    }
}
