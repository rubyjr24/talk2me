package com.serpies.talk2me.db.entity;

import com.serpies.talk2me.db.converter.LanguageConverter;
import com.serpies.talk2me.db.converter.ThemeConverter;
import com.serpies.talk2me.db.enums.Language;
import com.serpies.talk2me.db.enums.Theme;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "UserConfigs")
public class UserConfig implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "user_id")
    private Long userId;

    @Basic(optional = false)
    @Column(name = "auto_download")
    private Boolean autoDownload;

    @Column(name = "theme", nullable = false)
    @Convert(converter = ThemeConverter.class)
    private Theme theme;

    @Column(name = "language", nullable = false)
    @Convert(converter = LanguageConverter.class)
    private Language language;

    @JoinColumn(name = "id_profile_picture", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private File idProfilePicture;

    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    public UserConfig() {
    }

    public UserConfig(Long userId) {
        this.userId = userId;
    }

    public UserConfig(Long userId, boolean autoDownload, Theme theme, Language language) {
        this.userId = userId;
        this.autoDownload = autoDownload;
        this.theme = theme;
        this.language = language;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean isAutoDownload() {
        return autoDownload;
    }

    public void setAutoDownload(Boolean autoDownload) {
        this.autoDownload = autoDownload;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public File getIdProfilePicture() {
        return idProfilePicture;
    }

    public void setIdProfilePicture(File idProfilePicture) {
        this.idProfilePicture = idProfilePicture;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UserConfig that)) return false;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId);
    }

    @Override
    public String toString() {
        return "UserConfig{" +
                "userId=" + userId +
                ", autoDownload=" + autoDownload +
                ", theme='" + theme + '\'' +
                ", language='" + language + '\'' +
                ", idProfilePicture=" + idProfilePicture +
                ", user=" + user +
                '}';
    }

    @PrePersist // Establece un valor por defecto antes de persistir
    public void prePersist() {
        if (this.theme == null) this.theme = Theme.DEFAULT_SYSTEM_THEME;
        if (this.language == null) this.language = Language.DEFAULT_SYSTEM_THEME;
        if (this.autoDownload == null) this.autoDownload = true;
    }

}
