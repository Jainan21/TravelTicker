package com.example.travelticker.Model;

public class User {
    public String uid;
    public String name;
    public String email;
    public String avatarUrl;

    public User() {
        // Constructor rỗng cần thiết cho Firebase
    }

    public User(String name, String email, String avatarUrl) {
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
    }

    public User(String uid, String name, String email, String avatarUrl) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
