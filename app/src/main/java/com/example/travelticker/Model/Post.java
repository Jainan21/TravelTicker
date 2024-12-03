package com.example.travelticker.Model;

import java.util.ArrayList;

public class Post {
    private String noiDung, diaChi, img, ngayDang, tieuDe, idBaiDang, idNguoiDang, idTinh;
    private ArrayList<String> imgPhu;
    private ArrayList<dichVu> dichvu;

    public Post(String idBaiDang, String idNguoiDang, String idTinh, String noiDung, String diaChi, String img, String ngayDang, String tieuDe) {
        this.idBaiDang = idBaiDang;
        this.idNguoiDang = idNguoiDang;
        this.idTinh = idTinh;
        this.noiDung = noiDung;
        this.diaChi = diaChi;
        this.img = img;
        this.ngayDang = ngayDang;
        this.tieuDe = tieuDe;
    }

    public Post(String idNguoiDang, String noiDung, String diaChi, String img, String ngayDang, String tieuDe, ArrayList<String> imgPhu, ArrayList<dichVu> dichvu) {
        this.idNguoiDang = idNguoiDang;
        this.noiDung = noiDung;
        this.diaChi = diaChi;
        this.img = img;
        this.ngayDang = ngayDang;
        this.tieuDe = tieuDe;
        this.imgPhu = imgPhu;
        this.dichvu = dichvu;
    }

    public Post(String idNguoiDang, String noiDung, String diaChi, String img, String tieuDe, ArrayList<String> imgPhu, ArrayList<dichVu> dichvu) {
        this.idNguoiDang = idNguoiDang;
        this.noiDung = noiDung;
        this.diaChi = diaChi;
        this.img = img;
        this.tieuDe = tieuDe;
        this.imgPhu = imgPhu;
        this.dichvu = dichvu;
    }

    public Post(String idBaiDang, String idNguoiDang, String idTinh, String noiDung, String diaChi, String img, String ngayDang, String tieuDe, ArrayList<String> imgPhu, ArrayList<dichVu> dichvu) {
        this.idBaiDang = idBaiDang;
        this.idNguoiDang = idNguoiDang;
        this.idTinh = idTinh;
        this.noiDung = noiDung;
        this.diaChi = diaChi;
        this.img = img;
        this.ngayDang = ngayDang;
        this.tieuDe = tieuDe;
        this.imgPhu = imgPhu;
        this.dichvu = dichvu;
    }

    public Post(String idNguoiDang, String idTinh, String noiDung, String diaChi, String img, String ngayDang, String tieuDe, ArrayList<String> imgPhu, ArrayList<dichVu> dichvu) {
        this.idNguoiDang = idNguoiDang;
        this.idTinh = idTinh;
        this.noiDung = noiDung;
        this.diaChi = diaChi;
        this.img = img;
        this.ngayDang = ngayDang;
        this.tieuDe = tieuDe;
        this.imgPhu = imgPhu;
        this.dichvu = dichvu;
    }

    public Post(String idNguoiDang, String idTinh, String noiDung, String diaChi, String img, String ngayDang, String tieuDe) {
        this.idNguoiDang = idNguoiDang;
        this.idTinh = idTinh;
        this.noiDung = noiDung;
        this.diaChi = diaChi;
        this.img = img;
        this.ngayDang = ngayDang;
        this.tieuDe = tieuDe;
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

    public ArrayList<dichVu> getDichvu() {
        return dichvu;
    }

    public void setDichvu(ArrayList<dichVu> dichvu) {
        this.dichvu = dichvu;
    }
}
