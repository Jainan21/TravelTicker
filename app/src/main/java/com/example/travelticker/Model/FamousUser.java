package com.example.travelticker.Model;

public class FamousUser {
    String title, description, linkImage, idNguoiDang, idBaiDang;

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

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

    public String getIdNguoiDang() {
        return idNguoiDang;
    }

    public void setIdNguoiDang(String idNguoiDang) {
        this.idNguoiDang = idNguoiDang;
    }

    public String getIdBaiDang() {
        return idBaiDang;
    }

    public void setIdBaiDang(String idBaiDang) {
        this.idBaiDang = idBaiDang;
    }

    public FamousUser(String idBaiDang, String idNguoiDang, String linkImage, String description, String title) {
        this.idBaiDang = idBaiDang;
        this.idNguoiDang = idNguoiDang;
        this.linkImage = linkImage;
        this.description = description;
        this.title = title;
    }
}
