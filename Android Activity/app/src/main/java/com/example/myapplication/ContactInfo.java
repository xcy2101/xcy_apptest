package com.example.myapplication;

import androidx.annotation.NonNull;

public class ContactInfo {

    private  Integer id;
    private String contactName;   //用户名称

    private String Num;    //用户电话

    public ContactInfo(Integer id, String contactName, String num) {
        this.id = id;
        this.contactName = contactName;
        this.Num = num;
    }

    @NonNull
    @Override
    public String toString() {
        return "用户名="+contactName+"电话="+Num;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNum() {
        return Num;
    }

    public void setNum(String num) {
        Num = num;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

}