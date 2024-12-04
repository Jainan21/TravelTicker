package com.example.travelticker.Model;

import java.util.ArrayList;

public class Post {
    private String noiDung, diaChi, img, ngayDang, tieuDe, idBaiDang, idNguoiDang, idTinh;
    private ArrayList<String> imgPhu;
    private ArrayList<String> dichvu;

    public Post(){}

    public Post(String idBaiDang, String idNguoiDang, String noiDung, String diaChi, String img, String ngayDang, String tieuDe, ArrayList<String> imgPhu, ArrayList<String> dichvu) {
        this.idBaiDang = idBaiDang;
        this.idNguoiDang = idNguoiDang;
        this.noiDung = noiDung;
        this.diaChi = diaChi;
        this.img = img;
        this.ngayDang = ngayDang;
        this.tieuDe = tieuDe;
        this.imgPhu = imgPhu;
        this.dichvu = dichvu;
    }

    public Post(String idBaiDang, String idNguoiDang, String idTinh, String noiDung, String diaChi, String img, String ngayDang, String tieuDe, ArrayList<String> imgPhu, ArrayList<String> dichvu) {
        this.idBaiDang = idBaiDang;
        this.idNguoiDang = idNguoiDang;
        this.noiDung = noiDung;
        this.diaChi = diaChi;
        this.idTinh = idTinh;
        this.img = img;
        this.ngayDang = ngayDang;
        this.tieuDe = tieuDe;
        this.imgPhu = imgPhu;
        this.dichvu = dichvu;
    }

    public String getIdBaiDang() {
        return idBaiDang;
    }

    public void setIdBaiDang(String idBaiDang) {
        this.idBaiDang = idBaiDang;
    }

    public String getIdNguoiDang() {
        return idNguoiDang;
    }

    public void setIdNguoiDang(String idNguoiDang) {
        this.idNguoiDang = idNguoiDang;
    }

    public String getIdTinh() {
        return idTinh;
    }

    public void setIdTinh(String idTinh) {
        this.idTinh = idTinh;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNgayDang() {
        return ngayDang;
    }

    public void setNgayDang(String ngayDang) {
        this.ngayDang = ngayDang;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public ArrayList<String> getImgPhu() {
        return imgPhu;
    }

    public void setImgPhu(ArrayList<String> imgPhu) {
        this.imgPhu = imgPhu;
    }

    public ArrayList<String> getDichvu() {
        return dichvu;
    }

    public void setDichvu(ArrayList<String> dichvu) {
        this.dichvu = dichvu;
    }
}
