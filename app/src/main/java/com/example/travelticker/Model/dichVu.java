package com.example.travelticker.Model;

public class dichVu {
    private String img;
    private String name;
    private boolean isSelected;

    public dichVu(String img, String name) {
        this.img = img;
        this.name = name;
        this.isSelected = false;
    }

    public String getImg() {
        return img;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
