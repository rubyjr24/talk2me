package com.serpies.talk2me.db.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Language {
    ENGLISH("english"),
    SPANISH("spanish"),
    CATALAN("catalan"),
    DEFAULT_SYSTEM_THEME("default_system_theme");

    private final String value;

    Language(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator // para que use la función a la hora de pasar de JSON a Language
    public static Language fromString(String value) {

        for (Language language : Language.values()) {

            if (language.value.equalsIgnoreCase(value)) {
                return language;
            }

        }

        throw new IllegalArgumentException("Unknown language: " + value);
    }
}
