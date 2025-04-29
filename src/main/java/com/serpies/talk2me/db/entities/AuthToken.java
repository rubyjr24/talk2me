package com.serpies.talk2me.db.entities;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "auth_tokens")
public class AuthToken implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "user_id")
    private Long userId;

    @Basic(optional = false)
    private String token;

    @Basic(optional = false)
    @Column(name = "unsuccessful_attempts")
    private Short unsuccessfulAttempts;

    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expires_at")
    private Date expiresAt;

    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    public AuthToken() {
    }

    public AuthToken(Long userId) {
        this.userId = userId;
    }

    public AuthToken(Long userId, String token, short unsuccessfulAttempts, Date expiresAt, Date createdAt) {
        this.userId = userId;
        this.token = token;
        this.unsuccessfulAttempts = unsuccessfulAttempts;
        this.expiresAt = expiresAt;
        this.createdAt = createdAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Short getUnsuccessfulAttempts() {
        return unsuccessfulAttempts;
    }

    public void setUnsuccessfulAttempts(Short unsuccessfulAttempts) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AuthToken authToken)) return false;
        return Objects.equals(userId, authToken.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId);
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

    @PrePersist // Establece un valor por defecto antes de persistir
    public void prePersist() {
        if (this.createdAt == null) this.createdAt = new Date();
        if (this.unsuccessfulAttempts == null) this.unsuccessfulAttempts = 0;
    }

}
