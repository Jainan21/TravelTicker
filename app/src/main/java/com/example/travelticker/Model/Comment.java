package com.example.travelticker.Model;

public class Comment {
    private Integer linkAvt;
    private String rating, username, content, time;

    public Comment(Integer linkAvt, String rating, String username, String content, String time) {
        this.linkAvt = linkAvt;
        this.rating = rating;
        this.username = username;
        this.content = content;
        this.time = time;
    }

    public Integer getLinkAvt() {
        return linkAvt;
    }

    public void setLinkAvt(Integer linkAvt) {
        this.linkAvt = linkAvt;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
