package com.Bootcamp.Project.Application.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Converter(autoApply = true)
public class ListConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn( List<String> list) {

        if (list != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.writeValueAsString(list);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<String> convertToEntityAttribute(String s) {
        if (s != null) {
            try {

                String str[]=s.split(",");
                List<String> metadataValuesList= Arrays.asList(str);
                return metadataValuesList;

            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
