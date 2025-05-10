package com.serpies.talk2me.model;

import java.util.Set;

public class CreateChatRequestDto {

    private String name;
    private String description;
    private boolean isPrivate;
    private Set<Long> userIds;

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

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Set<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<Long> userIds) {
        this.userIds = userIds;
    }

    @Override
    public String toString() {
        return "CreateChatRequestDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isPrivate=" + isPrivate +
                ", userIds=" + userIds +
                '}';
    }
}
