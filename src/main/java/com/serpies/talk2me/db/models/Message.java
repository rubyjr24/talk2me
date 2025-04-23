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
@Table(name = "Messages")
@NamedQueries({
    @NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m"),
    @NamedQuery(name = "Message.findById", query = "SELECT m FROM Message m WHERE m.id = :id"),
    @NamedQuery(name = "Message.findByImportance", query = "SELECT m FROM Message m WHERE m.importance = :importance"),
    @NamedQuery(name = "Message.findByIsVisible", query = "SELECT m FROM Message m WHERE m.isVisible = :isVisible"),
    @NamedQuery(name = "Message.findByCreatedAt", query = "SELECT m FROM Message m WHERE m.createdAt = :createdAt")})
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    private short importance;
    @Basic(optional = false)
    private boolean isVisible;
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
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

    public Message(Integer id) {
        this.id = id;
    }

    public Message(Integer id, short importance, boolean isVisible, Date createdAt) {
        this.id = id;
        this.importance = importance;
        this.isVisible = isVisible;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public short getImportance() {
        return importance;
    }

    public void setImportance(short importance) {
        this.importance = importance;
    }

    public boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
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

    public List<TextMessage> getTextmessagesList() {
        return textMessageList;
    }

    public void setTextmessagesList(List<TextMessage> textMessageList) {
        this.textMessageList = textMessageList;
    }

    public List<ChatUser> getChatusersList() {
        return chatUserList;
    }

    public void setChatusersList(List<ChatUser> chatUserList) {
        this.chatUserList = chatUserList;
    }

    public List<FileMessage> getFilemessagesList() {
        return fileMessageList;
    }

    public void setFilemessagesList(List<FileMessage> fileMessageList) {
        this.fileMessageList = fileMessageList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
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
}
