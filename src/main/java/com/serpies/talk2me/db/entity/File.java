package com.serpies.talk2me.db.entity;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "files")
public class File implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long id;

    @Basic(optional = false)
    private String uri;

    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @OneToMany(mappedBy = "idProfilePicture", fetch = FetchType.LAZY)
    private List<UserConfig> userConfigList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "file", fetch = FetchType.LAZY)
    private List<FileMessage> fileMessageList;

    public File() {
    }

    public File(Long id) {
        this.id = id;
    }

    public File(Long id, String uri, Date createdAt) {
        this.id = id;
        this.uri = uri;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public List<UserConfig> getUserConfigList() {
        return userConfigList;
    }

    public void setUserConfigList(List<UserConfig> userConfigList) {
        this.userConfigList = userConfigList;
    }

    public List<FileMessage> getFileMessageList() {
        return fileMessageList;
    }

    public void setFileMessageList(List<FileMessage> fileMessageList) {
        this.fileMessageList = fileMessageList;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof File file)) return false;
        return Objects.equals(id, file.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
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

    @PrePersist // Establece un valor por defecto antes de persistir
    public void prePersist() {
        if (this.createdAt == null) this.createdAt = new Date();
    }

}
