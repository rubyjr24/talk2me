package com.serpies.talk2me.db.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;

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
    private Short importance;

    @Basic(optional = false)
    @Column(name = "is_visible")
    private Boolean isVisible;

    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "messageId", fetch = FetchType.LAZY)
    private List<ChatMessage> chatMessageList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "messageId", fetch = FetchType.LAZY)
    private List<TextMessage> textMessageList;

    @OneToMany(mappedBy = "idLastMessageSent", fetch = FetchType.LAZY)
    private List<ChatUser> chatUserList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "messageId", fetch = FetchType.LAZY)
    private List<FileMessage> fileMessageList;

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

    public List<ChatMessage> getChatMessageList() {
        return chatMessageList;
    }

    public void setChatMessageList(List<ChatMessage> chatMessageList) {
        this.chatMessageList = chatMessageList;
    }

    public List<TextMessage> getTextMessageList() {
        return textMessageList;
    }

    public void setTextMessageList(List<TextMessage> textMessageList) {
        this.textMessageList = textMessageList;
    }

    public List<ChatUser> getChatUserList() {
        return chatUserList;
    }

    public void setChatUserList(List<ChatUser> chatUserList) {
        this.chatUserList = chatUserList;
    }

    public List<FileMessage> getFileMessageList() {
        return fileMessageList;
    }

    public void setFileMessageList(List<FileMessage> fileMessageList) {
        this.fileMessageList = fileMessageList;
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
                ", importance=" + importance +
                ", isVisible=" + isVisible +
                ", createdAt=" + createdAt +
                ", chatMessageList=" + chatMessageList +
                ", textMessageList=" + textMessageList +
                ", chatUserList=" + chatUserList +
                ", fileMessageList=" + fileMessageList +
                '}';
    }

    @PrePersist // Establece un valor por defecto antes de persistir
    public void prePersist() {
        if (this.createdAt == null) this.createdAt = new Date();
        if (this.importance == null) this.importance = 1;
        if (this.isVisible == null) this.isVisible = true;
    }

}
