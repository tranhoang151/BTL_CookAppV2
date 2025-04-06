package com.example.btl_cookapp;

import java.io.Serializable;

public class MonAn implements Serializable {
    int ID;
    byte[] image;
    String tenMonAn;
    String congThuc;
    String user;
    TypeMonAn type;

    public TypeMonAn getType() {
        return type;
    }

    public void setType(TypeMonAn type) {
        this.type = type;
    }
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public String getCongThuc() {
        return congThuc;
    }

    public void setCongThuc(String congThuc) {
        this.congThuc = congThuc;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public MonAn(int ID, String tenMonAn, String congThuc, byte[] image, String user, TypeMonAn type) {
        this.ID = ID;
        this.image = image;
        this.tenMonAn = tenMonAn;
        this.congThuc = congThuc;
        this.user = user;
        this.type = type;
    }

    public MonAn(String tenMonAn, String congThuc, byte[] image, String user, TypeMonAn type) {
        this.ID = ID;
        this.image = image;
        this.tenMonAn = tenMonAn;
        this.congThuc = congThuc;
        this.user = user;
        this.type = type;
    }
}
