package com.serpies.talk2me.db.models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Ruben
 */
@Entity
@Table(name = "AuthTokens")
@NamedQueries({
    @NamedQuery(name = "AuthToken.findAll", query = "SELECT a FROM AuthToken a"),
    @NamedQuery(name = "AuthToken.findByUserId", query = "SELECT a FROM AuthToken a WHERE a.userId = :userId"),
    @NamedQuery(name = "AuthToken.findByToken", query = "SELECT a FROM AuthToken a WHERE a.token = :token"),
    @NamedQuery(name = "AuthToken.findByUnsuccessfulAttempts", query = "SELECT a FROM AuthToken a WHERE a.unsuccessfulAttempts = :unsuccessfulAttempts"),
    @NamedQuery(name = "AuthToken.findByExpiresAt", query = "SELECT a FROM AuthToken a WHERE a.expiresAt = :expiresAt"),
    @NamedQuery(name = "AuthToken.findByCreatedAt", query = "SELECT a FROM AuthToken a WHERE a.createdAt = :createdAt")})
public class AuthToken implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    private Integer userId;
    @Basic(optional = false)
    private String token;
    @Basic(optional = false)
    private short unsuccessfulAttempts;
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiresAt;
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JoinColumn(name = "userId", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    public AuthToken() {
    }

    public AuthToken(Integer userId) {
        this.userId = userId;
    }

    public AuthToken(Integer userId, String token, short unsuccessfulAttempts, Date expiresAt, Date createdAt) {
        this.userId = userId;
        this.token = token;
        this.unsuccessfulAttempts = unsuccessfulAttempts;
        this.expiresAt = expiresAt;
        this.createdAt = createdAt;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public short getUnsuccessfulAttempts() {
        return unsuccessfulAttempts;
    }

    public void setUnsuccessfulAttempts(short unsuccessfulAttempts) {
        this.unsuccessfulAttempts = unsuccessfulAttempts;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public User getUsers() {
        return user;
    }

    public void setUsers(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AuthToken)) {
            return false;
        }
        AuthToken other = (AuthToken) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AuthToken{" +
                "userId=" + userId +
                ", token='" + token + '\'' +
                ", unsuccessfulAttempts=" + unsuccessfulAttempts +
                ", expiresAt=" + expiresAt +
                ", createdAt=" + createdAt +
                ", user=" + user +
                '}';
    }
}
