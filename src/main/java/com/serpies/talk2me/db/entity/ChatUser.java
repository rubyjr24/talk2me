package com.serpies.talk2me.db.entity;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "chat_users")
public class ChatUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long id;

    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "joined_at")
    private Date joinedAt;

    @Basic(optional = false)
    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "id_last_message_sent")
    private Long idLastMessageSent;

    @Basic(optional = false)
    @Column(name = "user_id")
    private Long userId;

    @Basic(optional = false)
    @Column(name = "is_admin")
    private Boolean isAdmin;

    public ChatUser() {
    }

    public ChatUser(Long id) {
        this.id = id;
    }

    public ChatUser(Long id, Date joinedAt) {
        this.id = id;
        this.joinedAt = joinedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Date joinedAt) {
        this.joinedAt = joinedAt;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getIdLastMessageSent() {
        return idLastMessageSent;
    }

    public void setIdLastMessageSent(Long idLastMessageSent) {
        this.idLastMessageSent = idLastMessageSent;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getIsAdmin() {
        return this.isAdmin;
    }

    public void setIsAdmin(Boolean admin) {
        this.isAdmin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ChatUser chatUser)) return false;
        return Objects.equals(id, chatUser.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ChatUser{" +
                "id=" + id +
                ", joinedAt=" + joinedAt +
                ", chatId=" + chatId +
                ", idLastMessageSent=" + idLastMessageSent +
                ", userId=" + userId +
                ", isAdmin=" + isAdmin +
                '}';
    }

    @PrePersist // Establece un valor por defecto antes de persistir
    public void prePersist() {
        if (this.joinedAt == null) this.joinedAt = new Date();
        if (this.isAdmin == null) this.isAdmin = false;
    }

}
