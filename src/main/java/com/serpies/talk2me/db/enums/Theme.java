package com.serpies.talk2me.db.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Theme {
    DARK("dark"),
    LIGHT("light"),
    DEFAULT_SYSTEM_THEME("default_system_theme");

    private final String value;

    Theme(String value) {
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

    @JsonCreator // para que use la función a la hora de pasar de JSON a Theme
    public static Theme fromString(String value) {

        for (Theme theme : Theme.values()) {

            if (theme.value.equalsIgnoreCase(value)) {
                return theme;
            }

        }

        throw new IllegalArgumentException("Unknown theme: " + value);
    }
}