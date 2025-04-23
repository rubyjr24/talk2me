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
@Table(name = "Files")
@NamedQueries({
    @NamedQuery(name = "File.findAll", query = "SELECT f FROM File f"),
    @NamedQuery(name = "File.findById", query = "SELECT f FROM File f WHERE f.id = :id"),
    @NamedQuery(name = "File.findByUri", query = "SELECT f FROM File f WHERE f.uri = :uri"),
    @NamedQuery(name = "File.findByCreatedAt", query = "SELECT f FROM File f WHERE f.createdAt = :createdAt")})
public class File implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    private String uri;
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @OneToMany(mappedBy = "idProfilePicture", fetch = FetchType.LAZY)
    private List<UserConfig> userConfigList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fileId", fetch = FetchType.LAZY)
    private List<FileMessage> fileMessageList;

    public File() {
    }

    public File(Integer id) {
        this.id = id;
    }

    public File(Integer id, String uri, Date createdAt) {
        this.id = id;
        this.uri = uri;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<UserConfig> getUserconfigsList() {
        return userConfigList;
    }

    public void setUserconfigsList(List<UserConfig> userConfigList) {
        this.userConfigList = userConfigList;
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
        if (!(object instanceof File)) {
            return false;
        }
        File other = (File) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", uri='" + uri + '\'' +
                ", createdAt=" + createdAt +
                ", userConfigList=" + userConfigList +
                ", fileMessageList=" + fileMessageList +
                '}';
    }
}
