package com.example.firstapi.Utilities;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ToolStatusConvertor implements AttributeConverter<ToolStatus, String> {

    @Override
    public String convertToDatabaseColumn(ToolStatus toolStatus) {
        return toolStatus == null ? null : toolStatus.Status();
    }

    @Override
    public ToolStatus convertToEntityAttribute(String s) {
        return s == null ? null : ToolStatus.fromDb(s);
    }
}
