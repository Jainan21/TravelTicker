package com.example.travelticker.Model;

public class Location {
    private int linkImage;
    private String nameLocation;

    public Location(int linkImage, String nameLocation) {
        this.linkImage = linkImage;
        this.nameLocation = nameLocation;
    }


    public int getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(int linkImage) {
        this.linkImage = linkImage;
    }

    public String getNameLocation() {
        return nameLocation;
    }

    public void setNameLocation(String nameLocation) {
        this.nameLocation = nameLocation;
    }
}
