package com.example.travelticker;

public class User {
    public String name;
    public String email;

    public User() {
        // Constructor rỗng cần thiết cho Firebase
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
