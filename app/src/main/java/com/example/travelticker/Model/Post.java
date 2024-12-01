package com.example.travelticker.Model;

import java.util.ArrayList;

public class Post {
    private Integer idBaiDang, idNguoiDang, idTinh;
    private String noiDung, diaChi, img, ngayDang, tieuDe;
    private ArrayList<String> imgPhu;
    private ArrayList<dichVu> dichvu;

    public Post(Integer idBaiDang, Integer idNguoiDang, Integer idTinh, String noiDung, String diaChi, String img, String ngayDang, String tieuDe) {
        this.idBaiDang = idBaiDang;
        this.idNguoiDang = idNguoiDang;
        this.idTinh = idTinh;
        this.noiDung = noiDung;
        this.diaChi = diaChi;
        this.img = img;
        this.ngayDang = ngayDang;
        this.tieuDe = tieuDe;
    }

    public Post(Integer idNguoiDang, String noiDung, String diaChi, String img, String ngayDang, String tieuDe, ArrayList<String> imgPhu, ArrayList<dichVu> dichvu) {
        this.idNguoiDang = idNguoiDang;
        this.noiDung = noiDung;
        this.diaChi = diaChi;
        this.img = img;
        this.ngayDang = ngayDang;
        this.tieuDe = tieuDe;
        this.imgPhu = imgPhu;
        this.dichvu = dichvu;
    }

    public Post(Integer idNguoiDang, String noiDung, String diaChi, String img, String tieuDe, ArrayList<String> imgPhu, ArrayList<dichVu> dichvu) {
        this.idNguoiDang = idNguoiDang;
        this.noiDung = noiDung;
        this.diaChi = diaChi;
        this.img = img;
        this.tieuDe = tieuDe;
        this.imgPhu = imgPhu;
        this.dichvu = dichvu;
    }

    public Post(Integer idBaiDang, Integer idNguoiDang, Integer idTinh, String noiDung, String diaChi, String img, String ngayDang, String tieuDe, ArrayList<String> imgPhu, ArrayList<dichVu> dichvu) {
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

    public Post(Integer idNguoiDang, Integer idTinh, String noiDung, String diaChi, String img, String ngayDang, String tieuDe, ArrayList<String> imgPhu, ArrayList<dichVu> dichvu) {
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

    public Post(Integer idNguoiDang, Integer idTinh, String noiDung, String diaChi, String img, String ngayDang, String tieuDe) {
        this.idNguoiDang = idNguoiDang;
        this.idTinh = idTinh;
        this.noiDung = noiDung;
        this.diaChi = diaChi;
        this.img = img;
        this.ngayDang = ngayDang;
        this.tieuDe = tieuDe;
    }

    public Integer getIdBaiDang() {
        return idBaiDang;
    }

    public void setIdBaiDang(Integer idBaiDang) {
        this.idBaiDang = idBaiDang;
    }

    public Integer getIdNguoiDang() {
        return idNguoiDang;
    }

    public void setIdNguoiDang(Integer idNguoiDang) {
        this.idNguoiDang = idNguoiDang;
    }

    public Integer getIdTinh() {
        return idTinh;
    }

    public void setIdTinh(Integer idTinh) {
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
