package com.serpies.talk2me.db.converters;

import com.serpies.talk2me.db.enums.Language;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;


@Converter(autoApply = true)
public class LanguageConverter implements AttributeConverter<Language, String> {

    @Override
    public String convertToDatabaseColumn(Language language) {
        return language != null ? language.getValue() : null;
    }

    @Override
    public Language convertToEntityAttribute(String dbValue) {
        return dbValue != null ? Language.fromString(dbValue) : null;
    }
}