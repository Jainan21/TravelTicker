package com.example.travelticker.Model;

public class Service {
    String iconService;
    String nameService;
    String colorCode;

    public Service(String iconService, String nameService, String colorCode) {
        this.iconService = iconService;
        this.nameService = nameService;
        this.colorCode = colorCode;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getNameService() {
        return nameService;
    }

    public void setNameService(String nameService) {
        this.nameService = nameService;
    }

    public String getIconService() {
        return iconService;
    }

    public void setIconService(String iconService) {
        this.iconService = iconService;
    }
}
