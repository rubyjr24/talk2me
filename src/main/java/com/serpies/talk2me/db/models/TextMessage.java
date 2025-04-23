package com.serpies.talk2me.db.models;

import java.io.Serializable;
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

/**
 *
 * @author Ruben
 */
@Entity
@Table(name = "TextMessages")
@NamedQueries({
    @NamedQuery(name = "TextMessage.findAll", query = "SELECT t FROM TextMessage t"),
    @NamedQuery(name = "TextMessage.findById", query = "SELECT t FROM TextMessage t WHERE t.id = :id"),
    @NamedQuery(name = "TextMessage.findByMessage", query = "SELECT t FROM TextMessage t WHERE t.message = :message")})
public class TextMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    private String message;
    @JoinColumn(name = "messageId", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Message messageId;

    public TextMessage() {
    }

    public TextMessage(Integer id) {
        this.id = id;
    }

    public TextMessage(Integer id, String message) {
        this.id = id;
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Message getMessageId() {
        return messageId;
    }

    public void setMessageId(Message messageId) {
        this.messageId = messageId;
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
        if (!(object instanceof TextMessage)) {
            return false;
        }
        TextMessage other = (TextMessage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", messageId=" + messageId +
                '}';
    }
}
