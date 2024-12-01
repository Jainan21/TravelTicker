package com.example.travelticker.Model;

import java.util.Map;

public class Tinh {
    private String anh;
    private String ten;

    public Tinh(String anh, String ten) {
        this.anh = anh;
        this.ten = ten;
    }

    public Tinh() {
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
}