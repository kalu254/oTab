package com.kalu.otab.model;

import android.net.Uri;

public class User {

    private String name;
    private String department;
    private String reg_no;
    private String img_url;

    public User(String name, String department, String reg_no, String img_url) {
        this.name = name;
        this.department = department;
        this.reg_no = reg_no;
        this.img_url = img_url;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getReg_no() {
        return reg_no;
    }

    public void setReg_no(String reg_no) {
        this.reg_no = reg_no;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}

