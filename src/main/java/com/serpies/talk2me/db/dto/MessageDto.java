package com.serpies.talk2me.db.dto;

import java.util.Date;

public class MessageDto {

    private Long chatId;
    private Long userId;
    private Date createdAt;
    private String message;
    private Short importance;

    public MessageDto() {
    }

    public MessageDto(Long chatId, Long userId, Date createdAt, String message, Short importance) {
        this.chatId = chatId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.message = message;
        this.importance = importance;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Short getImportance() {
        return importance;
    }

    public void setImportance(Short importance) {
        this.importance = importance;
    }
}
