package com.serpies.talk2me.db.models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Ruben
 */
@Entity
@Table(name = "ChatUsers")
@NamedQueries({
    @NamedQuery(name = "ChatUser.findAll", query = "SELECT c FROM ChatUser c"),
    @NamedQuery(name = "ChatUser.findById", query = "SELECT c FROM ChatUser c WHERE c.id = :id"),
    @NamedQuery(name = "ChatUser.findByJoinedAt", query = "SELECT c FROM ChatUser c WHERE c.joinedAt = :joinedAt")})
public class ChatUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date joinedAt;
    @JoinColumn(name = "chatId", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Chat chatId;
    @JoinColumn(name = "idLastMessageSent", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Message idLastMessageSent;
    @JoinColumn(name = "userId", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User userId;

    public ChatUser() {
    }

    public ChatUser(Integer id) {
        this.id = id;
    }

    public ChatUser(Integer id, Date joinedAt) {
        this.id = id;
        this.joinedAt = joinedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ChatUser)) {
            return false;
        }
        ChatUser other = (ChatUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
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
}
