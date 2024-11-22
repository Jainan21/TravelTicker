package com.example.travelticker.Model;

public class FamousUser {
    Integer linkImage, linkAvt;
    String title, description, name;

    public FamousUser(Integer linkImage, Integer linkAvt, String title, String description, String name) {
        this.linkImage = linkImage;
        this.linkAvt = linkAvt;
        this.title = title;
        this.description = description;
        this.name = name;
    }

    public Integer getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(Integer linkImage) {
        this.linkImage = linkImage;
    }

    public Integer getLinkAvt() {
        return linkAvt;
    }

    public void setLinkAvt(Integer linkAvt) {
        this.linkAvt = linkAvt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
