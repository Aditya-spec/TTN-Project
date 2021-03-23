package com.Bootcamp.Project.Application.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class JsonConverter implements AttributeConverter<JSONArray, String> {

    @Override
    public String convertToDatabaseColumn(JSONArray jsonArray) {

        if (jsonArray != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.writeValueAsString(jsonArray);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public JSONArray convertToEntityAttribute(String s) {
        if (s != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(s, JSONArray.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
