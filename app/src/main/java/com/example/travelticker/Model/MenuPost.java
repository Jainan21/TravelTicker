package com.example.travelticker.Model;

public class MenuPost {
    String title;
    Integer iconUrl;

    public MenuPost(String title, Integer iconUrl) {
        this.title = title;
        this.iconUrl = iconUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(Integer iconUrl) {
        this.iconUrl = iconUrl;
    }
}
