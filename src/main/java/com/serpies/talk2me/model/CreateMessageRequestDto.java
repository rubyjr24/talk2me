package com.serpies.talk2me.model;

import java.util.Arrays;

public class CreateMessageRequestDto {

    private String message;
    private Long chatId;
    private byte[] image;
    private String fileExtension;

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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    @Override
    public String toString() {
        return "CreateMessageRequestDto{" +
                "message='" + message + '\'' +
                ", chatId=" + chatId +
                ", image=" + Arrays.toString(image) +
                ", fileExtension='" + fileExtension + '\'' +
                '}';
    }
}
