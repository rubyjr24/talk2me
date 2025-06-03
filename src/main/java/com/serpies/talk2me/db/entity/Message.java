package com.serpies.talk2me.db.entity;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "messages")
public class Message implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long id;

    @Basic(optional = false)
    @Column(name = "chat_id")
    private Long chatId;

    @Basic(optional = false)
    @Column(name = "user_id")
    private Long userId;

    @Basic(optional = false)
    private Short importance;

    @Basic(optional = false)
    @Column(name = "is_visible")
    private Boolean isVisible;

    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;


    public Message() {
    }

    public Message(Long id) {
        this.id = id;
    }

    public Message(Long id, short importance, boolean isVisible, Date createdAt) {
        this.id = id;
        this.importance = importance;
        this.isVisible = isVisible;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getImportance() {
        return importance;
    }

    public void setImportance(Short importance) {
        this.importance = importance;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Message message)) return false;
        return Objects.equals(id, message.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", userId=" + userId +
                ", importance=" + importance +
                ", isVisible=" + isVisible +
                ", createdAt=" + createdAt +
                '}';
    }

    @PrePersist // Establece un valor por defecto antes de persistir
    public void prePersist() {
        if (this.createdAt == null) this.createdAt = new Date();
        if (this.importance == null) this.importance = 1;
        if (this.isVisible == null) this.isVisible = true;
    }

}
