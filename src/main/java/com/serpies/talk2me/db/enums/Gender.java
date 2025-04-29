package com.serpies.talk2me.db.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    MALE("male"),
    FEMALE("female"),
    OTHER("other"),
    PREFER_NOT_TO_SAY("prefer_not_to_say");

    private final String value;

    Gender(String value) {
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

    @JsonCreator // para que use la función a la hora de pasar de JSON a Gender
    public static Gender fromString(String value) {

        for (Gender gender : Gender.values()) {

            if (gender.value.equalsIgnoreCase(value)) {
                return gender;
            }

        }

        throw new IllegalArgumentException(String.format("Unknown gender: %s", value));
    }
}