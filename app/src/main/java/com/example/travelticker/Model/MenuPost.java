package com.example.travelticker.Model;

import java.util.ArrayList;

public class MenuPost {
    String title;
    Integer iconUrl;
    Integer quantity;
    ArrayList<dichVu> imageList;
    ArrayList<dichVu> selectedImages;

    public MenuPost(String title, Integer iconUrl) {
        this.title = title;
        this.iconUrl = iconUrl;
        this.selectedImages = new ArrayList<>();
    }

    public MenuPost(String title, Integer iconUrl, Integer quantity) {
        this.title = title;
        this.iconUrl = iconUrl;
        this.quantity = quantity;
    }

    public ArrayList<dichVu> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<dichVu> imageList) {
        this.imageList = imageList;
    }

    public ArrayList<dichVu> getSelectedImages() {
        return selectedImages;
    }

    public void setSelectedImages(ArrayList<dichVu> selectedImages) {
        this.selectedImages = selectedImages;
    }

    public void addSeletedImage(dichVu dv) {
        if (selectedImages == null) {
            selectedImages = new ArrayList<>();
        }
        selectedImages.add(dv);
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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
