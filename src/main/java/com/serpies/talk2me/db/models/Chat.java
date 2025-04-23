package com.serpies.talk2me.db.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Ruben
 */
@Entity
@Table(name = "Chats")
@NamedQueries({
    @NamedQuery(name = "Chat.findAll", query = "SELECT c FROM Chat c"),
    @NamedQuery(name = "Chat.findById", query = "SELECT c FROM Chat c WHERE c.id = :id"),
    @NamedQuery(name = "Chat.findByName", query = "SELECT c FROM Chat c WHERE c.name = :name"),
    @NamedQuery(name = "Chat.findByDescription", query = "SELECT c FROM Chat c WHERE c.description = :description"),
    @NamedQuery(name = "Chat.findByIsPrivate", query = "SELECT c FROM Chat c WHERE c.isPrivate = :isPrivate"),
    @NamedQuery(name = "Chat.findByCreatedAt", query = "SELECT c FROM Chat c WHERE c.createdAt = :createdAt")})
public class Chat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    private String name;
    @Basic(optional = false)
    private String description;
    @Basic(optional = false)
    private boolean isPrivate;
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chatId", fetch = FetchType.LAZY)
    private List<ChatMessage> chatMessageList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chatId", fetch = FetchType.LAZY)
    private List<ChatUser> chatUserList;

    public Chat() {
    }

    public Chat(Integer id) {
        this.id = id;
    }

    public Chat(Integer id, String name, String description, boolean isPrivate, Date createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isPrivate = isPrivate;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<ChatMessage> getChatmessagesList() {
        return chatMessageList;
    }

    public void setChatmessagesList(List<ChatMessage> chatMessageList) {
        this.chatMessageList = chatMessageList;
    }

    public List<ChatUser> getChatusersList() {
        return chatUserList;
    }

    public void setChatusersList(List<ChatUser> chatUserList) {
        this.chatUserList = chatUserList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Chat)) {
            return false;
        }
        Chat other = (Chat) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isPrivate=" + isPrivate +
                ", createdAt=" + createdAt +
                ", chatMessageList=" + chatMessageList +
                ", chatUserList=" + chatUserList +
                '}';
    }
}
