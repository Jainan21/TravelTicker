package com.example.travelticker.Model;

public class dichVu {
    private String anh;
    private String ten;
    private String nen;
    private boolean isSelected;

    public dichVu(){}

    public dichVu(String img, String name) {
        this.anh = img;
        this.ten = name;
        this.isSelected = false;
    }

    public dichVu(String anh, String ten, String nen) {
        this.anh = anh;
        this.ten = ten;
        this.nen = nen;
        this.isSelected = false;
    }

    public String getImg() {
        return anh;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setImg(String img) {
        this.anh = img;
    }

    public String getName() {
        return ten;
    }

    public void setName(String name) {
        this.ten = name;
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

    public String getNen() {
        return nen;
    }

    public void setNen(String nen) {
        this.nen = nen;
    }
}
