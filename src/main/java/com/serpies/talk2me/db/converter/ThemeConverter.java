package com.serpies.talk2me.db.converter;

import com.serpies.talk2me.db.enums.Theme;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ThemeConverter implements AttributeConverter<Theme, String> {

    @Override
    public String convertToDatabaseColumn(Theme theme) {
        return theme != null ? theme.getValue() : null;
    }

    @Override
    public Theme convertToEntityAttribute(String dbValue) {
        return dbValue != null ? Theme.fromString(dbValue) : null;
    }
}