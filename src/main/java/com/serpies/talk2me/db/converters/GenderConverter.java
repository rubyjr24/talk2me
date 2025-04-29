package com.serpies.talk2me.db.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.serpies.talk2me.db.enums.Gender;

@Converter(autoApply = true)
public class GenderConverter implements AttributeConverter<Gender, String> {

    @Override
    public String convertToDatabaseColumn(Gender gender) {
        return gender != null ? gender.getValue() : null;
    }

    @Override
    public Gender convertToEntityAttribute(String dbValue) {
        return dbValue != null ? Gender.fromString(dbValue) : null;
    }
}
