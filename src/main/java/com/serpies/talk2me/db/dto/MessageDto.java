package com.serpies.talk2me.db.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageDto {

    private Long chatId;
    private Long userId;
    private Date createdAt;
    private Short importance;

    private String message;
    private byte[] image;
    private String fileName;

    public MessageDto() {
    }

    public MessageDto(Long chatId, Long userId, Date createdAt, String message, Short importance) {
        this.chatId = chatId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.message = message;
        this.importance = importance;
    }

    public MessageDto(Long chatId, Long userId, Date createdAt, Short importance, byte[] image, String fileName) {
        this.chatId = chatId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.importance = importance;
        this.image = image;
        this.fileName = fileName;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
