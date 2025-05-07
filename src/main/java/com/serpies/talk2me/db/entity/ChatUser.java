package com.serpies.talk2me.db.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.*;

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

    @JoinColumn(name = "chat_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Chat chatId;

    @JoinColumn(name = "id_last_message_sent", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Message idLastMessageSent;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User userId;

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

    public Chat getChatId() {
        return chatId;
    }

    public void setChatId(Chat chatId) {
        this.chatId = chatId;
    }

    public Message getIdLastMessageSent() {
        return idLastMessageSent;
    }

    public void setIdLastMessageSent(Message idLastMessageSent) {
        this.idLastMessageSent = idLastMessageSent;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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
                '}';
    }

    @PrePersist // Establece un valor por defecto antes de persistir
    public void prePersist() {
        if (this.joinedAt == null) this.joinedAt = new Date();
    }

}
