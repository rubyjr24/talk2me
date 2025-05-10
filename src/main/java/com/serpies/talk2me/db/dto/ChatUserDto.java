package com.serpies.talk2me.db.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatUserDto {

    private Long chatId;
    private Boolean isAdmin;
    private UserDto user;

    public ChatUserDto() {
    }

    public ChatUserDto(Long chatId, Boolean isAdmin, UserDto user) {
        this.chatId = chatId;
        this.isAdmin = isAdmin;
        this.user = user;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
