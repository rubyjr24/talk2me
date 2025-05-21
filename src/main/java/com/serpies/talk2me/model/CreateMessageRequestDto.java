package com.serpies.talk2me.model;

public class CreateMessageRequestDto {

    private String message;
    private Long chatId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    @Override
    public String toString() {
        return "CreateMessageRequestDto{" +
                "message='" + message + '\'' +
                ", chatId=" + chatId +
                '}';
    }
}
