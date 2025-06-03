package com.serpies.talk2me.db.entity;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "chats")
public class Chat implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long id;

    @Basic(optional = false)
    private String name;

    @Basic(optional = false)
    private String description;

    @Basic(optional = false)
    @Column(name = "is_private")
    private boolean isPrivate;

    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chatId", fetch = FetchType.LAZY)
    private List<ChatUser> chatUserList;

    public Chat() {
    }

    public Chat(Long id) {
        this.id = id;
    }

    public Chat(Long id, String name, String description, boolean isPrivate, Date createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isPrivate = isPrivate;
        this.createdAt = createdAt;
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

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<ChatUser> getChatUserList() {
        return chatUserList;
    }

    public void setChatUserList(List<ChatUser> chatUserList) {
        this.chatUserList = chatUserList;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Chat chat)) return false;
        return Objects.equals(id, chat.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isPrivate=" + isPrivate +
                ", createdAt=" + createdAt +
                ", chatUserList=" + chatUserList +
                '}';
    }

    @PrePersist // Establece un valor por defecto antes de persistir
    public void prePersist() {
        if (this.createdAt == null) this.createdAt = new Date();
    }

}
