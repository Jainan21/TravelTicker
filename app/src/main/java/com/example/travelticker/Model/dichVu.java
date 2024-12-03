package com.example.travelticker.Model;

public class dichVu {
    private String idDichVu;
    private String anh;
    private String ten;
    private String nen;
    private boolean isSelected;

    public dichVu(){}

    public dichVu(String idDichVu, String anh, String ten, String nen) {
        this.idDichVu = idDichVu;
        this.anh = anh;
        this.ten = ten;
        this.nen = nen;
        this.isSelected = false;
    }


    public String getIdDichVu() {
        return idDichVu;
    }

    public void setIdDichVu(String idDichVu) {
        this.idDichVu = idDichVu;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public String getAnh() {
        return anh;
    }


    public String getTen() {
        return ten;
    }


    public String getNen() {
        return nen;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public void setNen(String nen) {
        this.nen = nen;
    }
}
