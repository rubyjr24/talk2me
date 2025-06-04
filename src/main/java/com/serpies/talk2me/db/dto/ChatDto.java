package com.serpies.talk2me.db.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatDto {

    private Long id;
    private String name;
    private String description;
    private Boolean isPrivate;
    private Set<ChatUserDto> users;

    private Set<String> userEmails;


    public ChatDto() {
    }

    public ChatDto(Long id, String name, String description, Boolean isPrivate, Set<ChatUserDto> users) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isPrivate = isPrivate;
        this.users = users;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Set<ChatUserDto> getUsers() {
        return users;
    }

    public void setUsers(Set<ChatUserDto> users) {
        this.users = users;
    }

    public Set<String> getUserEmails() {
        return userEmails;
    }

    public void setUserIds(Set<String> userEmails) {
        this.userEmails = userEmails;
    }
}
