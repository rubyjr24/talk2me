package com.serpies.talk2me.db.entity;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "text_messages")
public class TextMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "message_id")
    private Long messageId;

    @Basic(optional = false)
    @Column(name = "message")
    private String messageData;

    @JoinColumn(name = "message_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Message message;

    public TextMessage() {
    }

    public TextMessage(Long messageId, String messageData, Message message) {
        this.messageId = messageId;
        this.messageData = messageData;
        this.message = message;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getMessageData() {
        return messageData;
    }

    public void setMessageData(String messageData) {
        this.messageData = messageData;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TextMessage that)) return false;
        return Objects.equals(messageId, that.messageId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(messageId);
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "messageId=" + messageId +
                ", messageData='" + messageData + '\'' +
                ", message=" + message +
                '}';
    }
}
