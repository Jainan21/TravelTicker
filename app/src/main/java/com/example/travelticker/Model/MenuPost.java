package com.example.travelticker.Model;

import android.net.Uri;

import java.util.ArrayList;

public class MenuPost {
    String title;
    Integer iconUrl;
    Integer quantity;
    ArrayList<dichVu> imageList;
    ArrayList<dichVu> selectedImages;
    ArrayList<Uri> listAnotherImage;
    ArrayList<String> idDV;

    public MenuPost(String title, Integer iconUrl) {
        this.title = title;
        this.iconUrl = iconUrl;
        this.selectedImages = new ArrayList<>();
        this.listAnotherImage = new ArrayList<>();
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

    public ArrayList<Uri> getListAnotherImage() {
        return listAnotherImage;
    }

    public void setListAnotherImage(ArrayList<Uri> listAnotherImage) {
        this.listAnotherImage = listAnotherImage;
    }

    public void addAnotherImage(Uri image){
        if (listAnotherImage == null){
            listAnotherImage = new ArrayList<>();
        }
        listAnotherImage.add(image);
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

    public ArrayList<String> getIdDV() {
        return idDV;
    }

    public void setIdDV(ArrayList<String> idDV) {
        this.idDV = idDV;
    }
}
