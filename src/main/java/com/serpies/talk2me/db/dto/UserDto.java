package com.serpies.talk2me.db.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.serpies.talk2me.db.enums.Gender;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private Long id;
    private String name;
    private String surname;
    private String password; // Solo usar en el sign-up
    private String email;
    private Gender gender;
    private Date lastConnection;

    public UserDto() {
    }

    public UserDto(Long id, String name, String surname, String email, Date lastConnection, Gender gender) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.lastConnection = lastConnection;
        this.gender = gender;
    }

    public UserDto(Long id, String name, String surname, String password, String email, Gender gender, Date lastConnection) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.lastConnection = lastConnection;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getLastConnection() {
        return lastConnection;
    }

    public void setLastConnection(Date lastConnection) {
        this.lastConnection = lastConnection;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", lastConnection=" + lastConnection +
                '}';
    }
}
