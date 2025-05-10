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

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Set<ChatUserDto> getUsers() {
        return users;
    }

    public void setUsers(Set<ChatUserDto> users) {
        this.users = users;
    }
}
