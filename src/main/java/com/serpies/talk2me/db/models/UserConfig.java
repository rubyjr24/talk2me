package com.serpies.talk2me.db.models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Ruben
 */
@Entity
@Table(name = "UserConfigs")
@NamedQueries({
    @NamedQuery(name = "UserConfig.findAll", query = "SELECT u FROM UserConfig u"),
    @NamedQuery(name = "UserConfig.findByUserId", query = "SELECT u FROM UserConfig u WHERE u.userId = :userId"),
    @NamedQuery(name = "UserConfig.findByAutoDownload", query = "SELECT u FROM UserConfig u WHERE u.autoDownload = :autoDownload"),
    @NamedQuery(name = "UserConfig.findByTheme", query = "SELECT u FROM UserConfig u WHERE u.theme = :theme"),
    @NamedQuery(name = "UserConfig.findByLenguage", query = "SELECT u FROM UserConfig u WHERE u.lenguage = :lenguage")})
public class UserConfig implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    private Integer userId;
    @Basic(optional = false)
    private boolean autoDownload;
    @Basic(optional = false)
    private String theme;
    @Basic(optional = false)
    private String lenguage;
    @JoinColumn(name = "idProfilePicture", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private File idProfilePicture;
    @JoinColumn(name = "userId", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    public UserConfig() {
    }

    public UserConfig(Integer userId) {
        this.userId = userId;
    }

    public UserConfig(Integer userId, boolean autoDownload, String theme, String lenguage) {
        this.userId = userId;
        this.autoDownload = autoDownload;
        this.theme = theme;
        this.lenguage = lenguage;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public boolean getAutoDownload() {
        return autoDownload;
    }

    public void setAutoDownload(boolean autoDownload) {
        this.autoDownload = autoDownload;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getLenguage() {
        return lenguage;
    }

    public void setLenguage(String lenguage) {
        this.lenguage = lenguage;
    }

    public File getIdProfilePicture() {
        return idProfilePicture;
    }

    public void setIdProfilePicture(File idProfilePicture) {
        this.idProfilePicture = idProfilePicture;
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
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserConfig)) {
            return false;
        }
        UserConfig other = (UserConfig) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserConfig{" +
                "userId=" + userId +
                ", autoDownload=" + autoDownload +
                ", theme='" + theme + '\'' +
                ", lenguage='" + lenguage + '\'' +
                ", idProfilePicture=" + idProfilePicture +
                ", user=" + user +
                '}';
    }
}
